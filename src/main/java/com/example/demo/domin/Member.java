package com.example.demo.domin;

import com.example.demo.application.dto.Auth;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String userId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String regNo;

    @Enumerated(EnumType.STRING)
    private Auth auth;


    public static Member create(final String userId,
                             final String password,
                             final String name,
                             final String regNo){

        final Member member = new Member();

        member.userId = userId;
        member.password = password;
        member.name = name;
        member.regNo = regNo;
        member.auth = Auth.MEMBER;

        return member;
    }
}
