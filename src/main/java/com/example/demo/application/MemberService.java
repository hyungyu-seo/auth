package com.example.demo.application;

import com.example.demo.application.dto.MemberCreateRequest;
import com.example.demo.application.dto.TaxIncomeDto;
import com.example.demo.application.dto.TokenDto;
import com.example.demo.application.exception.MemberException;
import com.example.demo.config.SecurityConfig;
import com.example.demo.domain.IncomeTax;
import com.example.demo.domain.Member;
import com.example.demo.domain.TaxManagement;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.infrastructure.auth.JwtTokenProvider;
import com.example.demo.infrastructure.TaxIncomeClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final SecurityConfig securityConfig;
    private final JwtTokenProvider jwtTokenProvider;
    private final EncryptService encryptService;
    private final TaxIncomeClient taxIncomeClient;


    public MemberService(MemberRepository memberRepository, SecurityConfig securityConfig, JwtTokenProvider jwtTokenProvider, EncryptService encryptService, TaxIncomeClient taxIncomeClient) {
        this.memberRepository = memberRepository;
        this.securityConfig = securityConfig;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encryptService = encryptService;
        this.taxIncomeClient = taxIncomeClient;
    }

    public String getDeterminedTaxAmount(String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow();

        BigDecimal calculatedTaxAmount = TaxManagement.calculateTaxAmount(member.getIncomeTax().getComprehensiveIncomeAmount(),
                member.getIncomeTax().getIncomeDeductionCreditCard(),
                member.getIncomeTax().getIncomeDeductionNationalPension(),
                member.getIncomeTax().getTaxCredit());

        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(calculatedTaxAmount);
    }


    @Transactional
    public void searchMemberInfo(String userId, String name, String regNo) throws JSONException, JsonProcessingException {

        JSONObject jsonObject = taxIncomeClient.apiCallData(name, encryptService.decryptRegNo(regNo));
        TaxIncomeDto taxIncome = TaxManagement.getTaxIncome(jsonObject);

        memberRepository.findByUserId(userId).ifPresent(member ->
                member.assignIncomeTax(IncomeTax.create(taxIncome.comprehensiveIncomeAmount(),
                                taxIncome.incomeDeductionCreditCard(),
                                taxIncome.incomeDeductionNationalPension(),
                                taxIncome.taxCredit())));
    }



    @Transactional
    public TokenDto login(String userId, String password) throws Exception {

        Optional<Member> member = memberRepository.findByUserId(userId);

        if(member.isEmpty()){
            throw new Exception("존재하지 않는 아이디입니다.");
        }

        if(!securityConfig.matches(password, member.get().getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }

        return jwtTokenProvider.generateToken(member.get(), encryptService.decryptRegNo(member.get().getRegNo()));
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
            throw new MemberException.MemberErrorException("값이 없는 정보가 있습니다.");
        }

        boolean duplicateCheck = memberRepository.findByUserId(request.userId()).isPresent();
        Map<Object, Object> users = new HashMap<>();
        users.put("동탁", "921108-1582816");
        users.put("관우", "681108-1582816");
        users.put("손권", "890601-2455116");
        users.put("유비", "790411-1656116");
        users.put("조조", "810326-2715702");

        if( duplicateCheck ) {
            throw new MemberException.MemberErrorException("이미 가입되어 있는 유저 입니다.");
        }

        if(users.containsKey(request.name())) {
            return users.get(request.name()).equals(request.regNo());
        } else throw new RuntimeException("가입할수 없는 유저입니다.");
    }

    public boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
