package com.example.demo.application;

import com.example.demo.application.dto.CustomMemberDetails;
import com.example.demo.application.dto.CustomMemberDto;
import com.example.demo.domain.Member;
import com.example.demo.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));

        CustomMemberDto dto = CustomMemberDto.of(member.getUserId(), member.getAuth() ,member.getName(), member.getRegNo());

        return new CustomMemberDetails(dto);
    }
}
