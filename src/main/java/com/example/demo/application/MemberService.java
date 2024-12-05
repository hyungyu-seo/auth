package com.example.demo.application;

import com.example.demo.application.dto.MemberCreateRequest;
import com.example.demo.application.dto.TokenInfo;
import com.example.demo.config.SecurityConfig;
import com.example.demo.domin.Member;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final SecurityConfig securityConfig;
    private final JwtTokenProvider jwtTokenProvider;
    private final EncryptService encryptService;


    public MemberService(MemberRepository memberRepository, SecurityConfig securityConfig, JwtTokenProvider jwtTokenProvider, EncryptService encryptService) {
        this.memberRepository = memberRepository;
        this.securityConfig = securityConfig;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encryptService = encryptService;
    }

    @Transactional
    public TokenInfo login(String userId, String password) throws Exception {

        Optional<Member> member = memberRepository.findByUserId(userId);

        if(member.isEmpty()){
            throw new Exception("존재하지 않는 아이디입니다.");
        }

        if(!securityConfig.matches(password, member.get().getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(member.get(), encryptService.decryptRegNo(member.get().getRegNo()));

        return tokenInfo;
    }

    @Transactional
    public Member createMember(final MemberCreateRequest request) {
        if (!checkUser(request)) {
            return null;
        }

        final Member member = Member.create(request.userId(),
                securityConfig.passwordEncoder().encode(request.password()),
                request.name(),
                encryptService.encryptRegNo(request.regNo()));

        memberRepository.save(member);
        return member;
    }

    // 사람 중복저장 막기
    // 특정 사람만 저장
    public Boolean checkUser(MemberCreateRequest request) {
        if( isStringEmpty(request.userId()) || isStringEmpty(request.name()) || isStringEmpty(request.password()) || isStringEmpty(request.regNo())) {
            throw new RuntimeException("값이 없는 정보가 있습니다.");
        }

        Boolean duplicateCheck = memberRepository.findByUserId(request.userId()).isPresent();
        Map<Object, Object> users = new HashMap<>();
        users.put("동탁", "921108-1582816");
        users.put("관우", "681108-1582816");
        users.put("손권", "890601-2455116");
        users.put("유비", "790411-1656116");
        users.put("조조", "810326-2715702");

        if( duplicateCheck ) {
            throw new RuntimeException("이미 가입되어 있는 유저 입니다.");
        }

        if(users.containsKey(request.name())) {
            return users.get(request.name()).equals(request.regNo());
        } else throw new RuntimeException("가입할수 없는 유저입니다.");
    }

    public boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
