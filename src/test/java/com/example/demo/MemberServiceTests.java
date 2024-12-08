package com.example.demo;

import com.example.demo.application.EncryptService;
import com.example.demo.application.MemberService;
import com.example.demo.application.dto.MemberCreateRequest;
import com.example.demo.application.dto.TokenDto;
import com.example.demo.domain.Member;
import com.example.demo.domain.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private EncryptService encryptService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("member 생성 테스트 (성공)")
    public void createMember() {

        // given
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest("kw68", "123456", "관우", "681108-1582816");

        // when
        Member result = memberService.createMember(memberCreateRequest);

        // then
        assertEquals(result.getName(), "관우");
    }

    @Test
    @DisplayName("member 생성 테스트 (정의 되지 않은 멤버)")
    public void createMember_undeterminedMember() {

        // given
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest("kw68", "123456", "관주", "681108-1582816");

        // when & then
        assertThrows(Exception.class, () -> memberService.createMember(memberCreateRequest));
    }

    @Test
    @DisplayName("login 테스트 (성공)")
    public void login() throws Exception {
        // given
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest("kw68", "123456", "관우", "681108-1582816");
        memberService.createMember(memberCreateRequest);

        // when
        TokenDto token = memberService.login("kw68","123456");

        // then
        assertNotNull(token);
    }

    @Test
    @DisplayName("login 테스트 (member 없는 경우)")
    public void login_notMember() throws Exception {

        // when & then
        assertThrows(Exception.class, () -> memberService.login("kw68", "123456"));
    }

    @Test
    @DisplayName("login 테스트 (비밀번호가 일치하지 않는 경우)")
    public void login_PasswordNotMatching() throws Exception {
        // given
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest("kw68", "123456", "관우", "681108-1582816");
        memberService.createMember(memberCreateRequest);

        // when & then
        assertThrows(Exception.class, () -> memberService.login("kw68", "1234567"));
    }

    @Test
    @DisplayName("member 외부에서 데이터 조회 및 저장 테스트 (성공)")
    public void processIncomeCheck() throws JSONException, JsonProcessingException {
        // given
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest("kw68", "123456", "관우", "681108-1582816");
        Member member = memberService.createMember(memberCreateRequest);

        // when
        memberService.processIncomeCheck("kw68", "관우", encryptService.encryptRegNo("681108-1582816"));

        // then
        assertNotNull(member.getIncomeTax().getComprehensiveIncomeAmount());
        assertNotNull(member.getIncomeTax().getTaxCredit());
        assertNotNull(member.getIncomeTax().getIncomeDeductionCreditCard());
        assertNotNull(member.getIncomeTax().getIncomeDeductionNationalPension());
    }

    @Test
    @DisplayName("member 결정세액 조회 (성공)")
    public void getDeterminedTaxAmount() throws JSONException, JsonProcessingException {
        // given
        final MemberCreateRequest memberCreateRequest = new MemberCreateRequest("kw68", "123456", "관우", "681108-1582816");
        memberService.createMember(memberCreateRequest);
        memberService.processIncomeCheck("kw68", "관우", encryptService.encryptRegNo("681108-1582816"));

        // when
        String result = memberService.getDeterminedTaxAmount("kw68");

        // then
        assertNotNull(result);
    }
}
