package com.traplaner.memberservice.member.controller;

import com.traplaner.memberservice.member.service.KakaoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KakaoLoginController {

    @Value("${sns.kakao.Client-Id}")
    private  String kakaoClientId;
    @Value("${sns.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${sns.kakao.logout-redirect}")
    private String kakaoLogoutRedirectUri;
    private final KakaoService kakaoService;

    //이거 프론트 단으로 가야해....
    @GetMapping("/kakao/login")
    public String kakaoLogin() {
        // 카카오 인가코드 신청 -> 카카오 인증 서버에서 클라이언트와 로그인 과정을 거친 후
        // redirect uri로 요청을 보내게 됨.
        String uri = "https://kauth.kakao.com/oauth/authorize";
        uri += "?client_id=" + kakaoClientId;
        uri += "&redirect_uri=" + kakaoRedirectUri;
        uri += "&response_type=code";

        return "redirect:" + uri;
    }

    // 약속된 redirect uri로 인가 코드가 옵니다.
    @GetMapping("/auth/kakao")
    public String authCodeKakao(@RequestParam String code,
                                HttpSession session) {
        log.info("인가 코드: {}", code);

        // 인가 코드를 가지고 카카오 인증 서버에게 토큰 발급을 요청하자
        // (server to server 통신)
        Map<String, String> params = new HashMap<>();
        params.put("appKey", kakaoClientId);
        params.put("redirect", kakaoRedirectUri);
        params.put("code", code);

        kakaoService.login(params, session);

        // 로그인 처리 완료되면 홈화면으로 보내자
        return "redirect:/";

    }


}