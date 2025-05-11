package com.traplaner.memberservice.member.infrastructure;

import com.traplaner.memberservice.member.service.port.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository implements RefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public String findByEmail(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    @Override
    public void save(String email, String accessToken, int i, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(email, accessToken, i, timeUnit);
    }
}
