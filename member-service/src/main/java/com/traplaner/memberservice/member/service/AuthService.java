package com.traplaner.memberservice.member.service;

import com.traplaner.memberservice.common.exception.ExpiredRefreshTokenException;
import com.traplaner.memberservice.member.domain.Member;
import com.traplaner.memberservice.member.service.port.MemberRepository;
import com.traplaner.memberservice.member.service.port.RefreshTokenRepository;
import com.traplaner.memberservice.member.service.port.TokenProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public String refreshAccessToken(Integer memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("NO_ACC"));
        String refreshToken = refreshTokenRepository.findByEmail(member.getEmail());
        if (refreshToken == null) {
            throw new ExpiredRefreshTokenException(member.getEmail());
        }
        return tokenProvider.createToken(member.getId());
    }
}