package com.traplaner.memberservice.member.domain;

import lombok.*;

@Getter @ToString @Builder @AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private int id;
    private String nickName;
    private String email;
    private String loginMethod;
    private String profile;
    private String token;

    @Builder
    public LoginResponse(Member member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.loginMethod = member.getLoginMethod().toString();
        this.profile = member.getProfileImg();
    }
    public static LoginResponse from(Member member, String accessToken) {
        return new LoginResponse(
                member.getId(),
                member.getNickName(),
                member.getEmail(),
                String.valueOf(member.getLoginMethod()),
                member.getProfileImg(),
                accessToken
        );
    }
}