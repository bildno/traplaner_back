package com.traplaner.memberservice.member.service.port;

import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public interface RefreshTokenRepository {
    String findByEmail(String email);

    void save(String email, String accessToken, int i, TimeUnit timeUnit);
}
