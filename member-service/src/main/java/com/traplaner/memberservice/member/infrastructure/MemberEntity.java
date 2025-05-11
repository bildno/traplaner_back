package com.traplaner.memberservice.member.infrastructure;


import com.traplaner.memberservice.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

// 엔티티에 세터 추가 해도 되나?
@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="tbl_member")
public class MemberEntity {
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
    private Member.LoginMethod loginMethod;

    public static MemberEntity fromModel(Member member) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.id = member.getId();
        memberEntity.nickName = member.getNickName();
        memberEntity.profileImg = member.getProfileImg();
        memberEntity.password = member.getPassword();
        memberEntity.email = member.getEmail();
        memberEntity.loginMethod = member.getLoginMethod();
        return memberEntity;
    }

    public Member toModel() {
        return Member.builder()
                .id(this.id)
                .nickName(this.nickName)
                .profileImg(this.profileImg)
                .password(this.password)
                .email(this.email)
                .loginMethod(this.loginMethod)
                .build();
    }


    public MemberEntity fromEntity() {
        return MemberEntity.builder()
                .id(id)
                .nickName(nickName)
                .email(email)
                .loginMethod(loginMethod)
                .profileImg(profileImg)
                .build();
    }

}