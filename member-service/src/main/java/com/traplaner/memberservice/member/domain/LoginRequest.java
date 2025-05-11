package com.traplaner.memberservice.member.domain;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter @ToString
@Builder
public class LoginRequest {

    private String email;
    private String password;

}
