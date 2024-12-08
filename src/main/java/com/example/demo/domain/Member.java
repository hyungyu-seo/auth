package com.example.demo.domain;

import com.example.demo.application.dto.Auth;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

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

    @Embedded
    private IncomeTax incomeTax;

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

    public void update(final String userId,
                         final String password,
                         final String name,
                         final String regNo,
                         final Auth auth,
                         final IncomeTax incomeTax ){
        if (userId != null) this.userId = userId;
        if (password != null) this.password = password;
        if (name != null) this.name = name;
        if (regNo != null) this.regNo = regNo;
        if (auth != null) this.auth = auth;
        if (incomeTax != null) this.incomeTax = incomeTax;
    }

    public void assignIncomeTax(final IncomeTax incomeTax){
        this.incomeTax = incomeTax;
    }

}
