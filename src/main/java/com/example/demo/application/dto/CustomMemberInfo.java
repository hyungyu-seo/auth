package com.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomMemberInfo(@Schema(description = "사용자 아이디", example = "kw68")  String userId,
                               @Schema(description = "사용자 권한") Auth auth,
                               @Schema(description = "사용자 이름", example = "관우") String name,
                               @Schema(description = "사용자 주민등록번호") String regNo) {

    public static CustomMemberInfo of(String userId, Auth auth, String name, String regNo) {
        return new CustomMemberInfo( userId, auth, name, regNo);
    }
}