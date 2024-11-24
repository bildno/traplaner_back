package com.traplaner.memberservice.member.service;

import com.traplaner.memberservice.common.auth.JwtTokenProvider;
import com.traplaner.memberservice.member.dto.KakaoUserResponseDTO;
import com.traplaner.memberservice.member.entity.Member;
import com.traplaner.memberservice.member.dto.SignUpRequestDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.traplaner.memberservice.member.entity.Member.*;
import static com.traplaner.memberservice.member.entity.Member.LoginMethod.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    // 로그인 처리
    public void login(Map<String, String> params, HttpSession session) {

        String accessToken = getKakaoAccessToken(params);
        // 발급받은 액세스 토큰으로 사용자 정보 가져오기
        KakaoUserResponseDTO kakaoUser = getKakaoUserInfo(accessToken);
        session.setAttribute("access_token", accessToken);
        session.setAttribute("kakaoAccount", kakaoUser.getId());

        String kakaoUserEmail = kakaoUser.getAccount().getEmail();
        // 이제 카카오 인증 서버와의 연결은 더 필요하지 않습니다.
        // 문서에도 나와있었지만, 자체 로그인 처리 완료는 우리 서비스에서 마무리 지어 줘야 합니다.
        // 가져온 카카오 유저로 우리서비스에 이용자인지 확인하기
        //중복검사 다시해야함
        if (true) {
            // 커먼 아이디와 같은 이메일일 경우를 처리 해야함
            // 한 번도 카카오 로그인을 한 적이 없다면 회원 가입이 들어간다.
            memberService.join(SignUpRequestDto.builder()
                            .password(UUID.randomUUID().toString())
                            .nickName(kakaoUser.getProperties().getNickname())
                            .email(kakaoUser.getAccount().getEmail())
                            .loginMethod(KAKAO)
                            .build(), kakaoUser.getProperties().getProfileImage());
        }
        // 우리 사이트 로그인 처리
        log.info("카카오 로그인이에욥!");
        String token
                = jwtTokenProvider.createToken(kakaoUserEmail,accessToken);
        log.info("token: {}", token);

        String refreshToken
                = jwtTokenProvider.createRefreshToken(kakaoUserEmail);
        // refresh Token을 DB에 저장하자. -> redis에 저장.
        redisTemplate.opsForValue().set(kakaoUserEmail, refreshToken, 240, TimeUnit.HOURS);


    }
    //아마 프론트쪽에도 비슷한 로직 구현해야할듯.
    public void logout(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String url = "https://kapi.kakao.com/v1/user/logout";
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("카카오 로그아웃 성공: {}", response.getBody());
            } else {
                log.info("카카오 로그아웃 실패: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.info("카카오 로그아웃 중 오류 발생: {}", e.getMessage());
        }
    }


    private KakaoUserResponseDTO getKakaoUserInfo(String accessToken) {

        String requestUri = "https://kapi.kakao.com/v2/user/me";

        // 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 보내기
        RestTemplate template = new RestTemplate();
        ResponseEntity<KakaoUserResponseDTO> responseEntity = template.exchange(
                requestUri,
                HttpMethod.POST,
                new HttpEntity<>(headers),
                KakaoUserResponseDTO.class
        );
        KakaoUserResponseDTO userInfo = responseEntity.getBody();

        log.info("응답 데이터 결과: {}", userInfo);

        return userInfo;
    }

    // 토큰 발급 요청
    private String getKakaoAccessToken(Map<String, String> requestParam) {

        // 요청 URI
        String requestUri = "https://kauth.kakao.com/oauth/token";

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 요청 바디에 파라미터 세팅
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestParam.get("appKey"));
        params.add("redirect_uri", requestParam.get("redirect"));
        params.add("code", requestParam.get("code"));

        // 카카오 인증 서버로 Post 요청 날리기
        RestTemplate template = new RestTemplate();

        // 헤더 정보와 파라미터를 하나로 묶기
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, headers);

        /*
        - RestTemplate 객체가 REST API 통신을 위한 API인데 (자바스크립트 fetch역할)
                - 서버에 통신을 보내면서 응답을 받을 수 있는 메서드가 exchange
        param1: 요청 URL
        param2: 요청 방식 (get, post, put, patch, delete...)
        param3: 요청 헤더와 요청 바디 정보 - HttpEntity로 포장해서 줘야 함
        param4: 응답결과(JSON)를 어떤 타입으로 받아낼 것인지 (ex: DTO로 받을건지 Map으로 받을건지)
         */
        ResponseEntity<Map> responseEntity
                = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);

        // 응답 데이터에서 JSON 추출
        Map<String, Object> responseJSON = responseEntity.getBody();
        log.info("응답 JSON 데이터: {}", responseJSON);

        // access token 추출 (카카오 로그인 중인 사용자의 정보를 요청할 때 써야합니다.)
        String accessToken = (String) responseJSON.get("access_token");
        return accessToken;
    }

}

