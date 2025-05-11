package com.traplaner.memberservice.member.mock;

import com.traplaner.memberservice.member.service.port.RefreshTokenRepository;

import java.util.concurrent.TimeUnit;

public class FakeRefreshTokenRepository implements RefreshTokenRepository {
    @Override
    public String findByEmail(String email) {
        return "";
    }

    @Override
    public void save(String email, String accessToken, int i, TimeUnit timeUnit) {

    }
}
