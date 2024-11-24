package com.traplaner.memberservice.member.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter @ToString
@Builder
public class LoginRequestDto {

    private String email;
    private String password;

}
