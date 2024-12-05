package com.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenInfo(@Schema(description = "권한유형") String grantType,
                        @Schema(description = "토큰") String accessToken) {
    public static TokenInfo of(String grantType, String accessToken) {
        return new TokenInfo(grantType, accessToken);
    }
}
