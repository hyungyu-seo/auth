package com.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenDto(@Schema(description = "권한유형") String grantType,
                       @Schema(description = "토큰") String accessToken) {
    public static TokenDto of(String grantType, String accessToken) {
        return new TokenDto(grantType, accessToken);
    }
}
