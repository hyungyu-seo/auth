package com.example.demo.controller;

import com.example.demo.application.MemberService;
import com.example.demo.application.dto.CustomMemberDetails;
import com.example.demo.application.dto.MemberCreateRequest;
import com.example.demo.application.dto.MemberLoginRequest;
import com.example.demo.application.dto.TokenDto;
import com.example.demo.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/szs")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API")
    public ResponseEntity<Member> createMember(final @RequestBody MemberCreateRequest request) {
        Member member = memberService.createMember(request);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API")
    public TokenDto login(@RequestBody MemberLoginRequest memberLoginRequest) throws Exception {
        String memberId = memberLoginRequest.userId();
        String password = memberLoginRequest.password();
        return memberService.login(memberId, password);
    }

    @PostMapping("/scrap")
    @Operation(summary = "스크래핑 API")
    public ResponseEntity<String> memberTexInfoSave(Authentication authentication) throws IOException, JSONException {
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
        memberService.searchMemberInfo(userDetails.getUserId(), userDetails.getUsername(), userDetails.getRegNo());
        return ResponseEntity.ok().body("성공");
    }

    @GetMapping("/refund")
    @Operation(summary = "결정세액 조회 API")
    public ResponseEntity<String> getRefund(Authentication authentication) throws IOException, JSONException {

        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();
        String determinedTaxAmount = memberService.getDeterminedTaxAmount(userDetails.getUserId());
        return ResponseEntity.ok().body("{'결정세액' : \"" + determinedTaxAmount + "\"}");
    }

}

