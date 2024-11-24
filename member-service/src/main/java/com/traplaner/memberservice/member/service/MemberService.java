package com.traplaner.memberservice.member.service;
import com.traplaner.memberservice.member.dto.LoginRequestDto;
import com.traplaner.memberservice.member.dto.LoginUserResponseDTO;
import com.traplaner.memberservice.member.dto.SignUpRequestDto;
import com.traplaner.memberservice.member.entity.Member;
import com.traplaner.memberservice.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;

    public Member join(SignUpRequestDto dto, String savePath){
        return memberRepository.save(dto.toEntity(encoder,savePath));
    }

    public void kakaoLogout(LoginUserResponseDTO dto, HttpSession session) {

        String requestUri = "https://kapi.kakao.com/v1/user/logout";

        String accessToken = (String) session.getAttribute("access_token");
        long kakaoAcount = (long) session.getAttribute("kakaoAccount");
        log.info("accessToken: {}", accessToken);
        log.info("kakaoAcount: {}", kakaoAcount);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", kakaoAcount);

        session.invalidate();

        ResponseEntity<Map> responseEntity = new RestTemplate().exchange(
                requestUri,
                HttpMethod.POST,
                new HttpEntity<>(params, headers),
                Map.class);

    }


    public boolean changePassword(String email, String password) {

        Member foundMember = memberRepository.findByEmail(email).orElseThrow(()->
                new EntityNotFoundException("비밀번호 변경 실패!")
                );
        foundMember.setPassword(encoder.encode(password));
        memberRepository.save(foundMember);
        return true;
    }

    public Member login(LoginRequestDto dto) {
        Member member  = memberRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("User not found")
        );

        // 비밀번호 확인하기 (암호화 되어있으니 encoder에게 부탁)
        if (!encoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

    public Member findById(long id) {
        return memberRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Member not found")
        );
    }
}
