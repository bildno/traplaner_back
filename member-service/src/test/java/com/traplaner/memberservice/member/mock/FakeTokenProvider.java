package com.traplaner.memberservice.member.mock;

import com.traplaner.memberservice.member.service.port.TokenProvider;

public class FakeTokenProvider implements TokenProvider {
    @Override
    public String createToken(Integer memberId) {
        return "";
    }

    @Override
    public String createRefreshToken(String email) {
        return "";
    }
}
