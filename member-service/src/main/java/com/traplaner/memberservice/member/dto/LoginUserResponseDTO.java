package com.traplaner.memberservice.member.dto;

import com.traplaner.memberservice.member.entity.Member;
import lombok.*;

@Getter @ToString @Builder @AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponseDTO {
    private int id;
    private String nickName;
    private String email;
    private String loginMethod;
    private String profile;

    public LoginUserResponseDTO(Member member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.loginMethod = member.getLoginMethod().toString();
        this.profile = member.getProfileImg();
    }
}