package com.traplaner.memberservice.member.entity;

/*
-- 회원 관리 테이블
CREATE TABLE `tbl_member` (
        `id`        int        NOT NULL        COMMENT 'NOT NULL',
        `nick_name`        varchar(30)        NOT NULL,
        `profile_img`        varchar(500)        NULL,
        `login_method`        ENUM('KAKAO','NAVER', 'COMMON')        NOT NULL,
        `password`        varchar(50)        NOT NULL,
        `email`        varchar(200)        NOT NULL
);
 */

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

// 엔티티에 세터 추가 해도 되나?
@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="tbl_member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nickName;

    @Column(name = "profile_img", nullable = false)
    private String profileImg;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_method", nullable = false)
    private LoginMethod loginMethod;

    @Getter @ToString @AllArgsConstructor
    public enum LoginMethod {
        KAKAO("KAKAO", 1),
        NAVER("NAVER", 2),
        COMMON("COMMON", 3);

        private String type;
        private int value;
    }


    public Member fromEntity() {
        return Member.builder()
                .id(id)
                .nickName(nickName)
                .email(email)
                .loginMethod(loginMethod)
                .profileImg(profileImg)
                .build();
    }

}