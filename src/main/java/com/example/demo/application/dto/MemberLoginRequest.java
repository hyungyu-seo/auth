package com.example.demo.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record MemberLoginRequest (@Schema(description = "사용자 아이디", example = "kw68")  String userId,
                                  @Schema(description = "사용자 비밀번호", example = "123456") String password){
}
