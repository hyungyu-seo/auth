package com.example.demo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Auth {

    ADMIN("ROLE_ADMIN,ROLE_MEMBER"),
    MEMBER("ROLE_MEMBER");

    private String value;
}