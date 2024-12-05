package com.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberCreateRequest(@Schema(description = "사용자 아이디", example = "kw68")  String userId,
                               @Schema(description = "사용자 비밀번호", example = "123456") String password,
                               @Schema(description = "사용자 이름", example = "관우") String name,
                               @Schema(description = "사용자 주민등록번호", example = "681108-1582816") String regNo) {

    public static MemberCreateRequest of(String userId, String password, String name, String regNo) {
        return new MemberCreateRequest( userId, password, name, regNo);
    }
}
