package com.traplaner.memberservice.common.exception;

import com.traplaner.memberservice.member.domain.Member;

public class ExpiredRefreshTokenException extends RuntimeException {
    public ExpiredRefreshTokenException(String memberEmail) {
        super( memberEmail + " has expired refresh token");
    }

    public Member.LoginMethod loginMethod() {
        return Member.LoginMethod.KAKAO;
    }
}
