package com.traplaner.memberservice.member.service.port;

import org.springframework.stereotype.Component;

@Component
public interface TokenProvider {
    String createToken(Integer memberId);
    String createRefreshToken(String email);
}
