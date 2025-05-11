package com.traplaner.memberservice.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class Member {
    private final Integer id;
    private final String nickName;
    private final String profileImg;
    private final String password;
    private final String email;
    private final LoginMethod loginMethod;

    @Builder
    public Member(Integer id, String nickName, String profileImg, String password, String email, LoginMethod loginMethod) {
        this.id = id;
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.password = password;
        this.email = email;
        this.loginMethod = loginMethod;
    }

    public static Member create(MemberCreate dto, String imageUrl) {
        return Member.builder()
                .nickName(dto.getNickName())
                .profileImg(imageUrl)
                .password(dto.getPassword())
                .email(dto.getEmail())
                .loginMethod(dto.getLoginMethod())
                .build();
    }

    public Member changePassword(String newPassword) {
        //비밀번호 validation 로직 필요함
        return Member.builder()
                .id(id)
                .nickName(nickName)
                .profileImg(profileImg)
                .password(newPassword)
                .email(email)
                .loginMethod(loginMethod)
                .build();
    }

    public Member changeNickName(String newNickName) {
        //nickName validation 로직 필요함
        return Member.builder()
                .id(id)
                .nickName(newNickName)
                .profileImg(profileImg)
                .password(password)
                .email(email)
                .loginMethod(loginMethod)
                .build();
    }

    @Getter @ToString
    @AllArgsConstructor
    public enum LoginMethod {
        KAKAO("KAKAO", 1),
        NAVER("NAVER", 2),
        COMMON("COMMON", 3);

        private String type;
        private int value;
    }
}
