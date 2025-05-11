package com.traplaner.memberservice.member.controller.request;

import com.traplaner.memberservice.member.domain.Member;
import com.traplaner.memberservice.member.domain.MemberCreate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class MemberSignUpRequest {
    private String email;
    private String password;
    private String nickName;
    private MultipartFile profileImage;

    public MemberCreate toMemberCreate(String savedImagePath) {
        return new MemberCreate(email, password, nickName, savedImagePath, Member.LoginMethod.COMMON);
    }
}

