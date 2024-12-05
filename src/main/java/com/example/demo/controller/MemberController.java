package com.example.demo.controller;

import com.example.demo.application.MemberService;
import com.example.demo.application.dto.MemberCreateRequest;
import com.example.demo.application.dto.MemberLoginRequest;
import com.example.demo.application.dto.TokenInfo;
import com.example.demo.domin.Member;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/szs")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입 API")
    public ResponseEntity<Member> createCar(final @RequestBody MemberCreateRequest request) {
        Member member = memberService.createMember(request);
        return ResponseEntity.ok(member);
    }

    @PostMapping("/login")
    public TokenInfo login(@RequestBody MemberLoginRequest memberLoginRequest) throws Exception {
        String memberId = memberLoginRequest.userId();
        String password = memberLoginRequest.password();
        return memberService.login(memberId, password);
    }

    @PostMapping("/scrap")
    public ResponseEntity<String> writeReview(Authentication authentication,
                                              @RequestBody MemberLoginRequest memberLoginRequest){
        return ResponseEntity.ok().body(authentication.getName() + "님의 리뷰 등록이 완료 되었습니다.");
    }

}

