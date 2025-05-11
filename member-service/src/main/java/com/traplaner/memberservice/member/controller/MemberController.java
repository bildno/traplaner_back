package com.traplaner.memberservice.member.controller;

import com.traplaner.memberservice.member.controller.port.MemberService;
import com.traplaner.memberservice.member.controller.request.MemberSignUpRequest;
import com.traplaner.memberservice.member.infrastructure.JwtTokenProvider;
import com.traplaner.memberservice.common.config.AwsS3Config;
import com.traplaner.memberservice.common.dto.CommonErrorDto;
import com.traplaner.memberservice.common.dto.CommonResDto;
import com.traplaner.memberservice.common.exception.ExpiredRefreshTokenException;
import com.traplaner.memberservice.common.exception.MemberNotFoundException;
import com.traplaner.memberservice.member.controller.response.RefreshResponse;
import com.traplaner.memberservice.member.domain.RefreshRequest;
import com.traplaner.memberservice.member.service.AuthService;
import com.traplaner.memberservice.member.service.MailSenderService;
import com.traplaner.memberservice.member.domain.LoginRequest;
import com.traplaner.memberservice.member.domain.LoginResponse;
import com.traplaner.memberservice.member.domain.Member;
import com.traplaner.memberservice.member.service.KakaoService;
import com.traplaner.memberservice.member.service.MemberServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MailSenderService mailSenderService;
    private final MemberService memberService;
    private final KakaoService kakaoService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final Environment env;
    private final AwsS3Config s3Config;

//    @Qualifier("member-template")
    private final RedisTemplate redisTemplate;

    //비밀번호 변경로직
    @Transactional
    @PutMapping("/pw-change")
    @ResponseBody
    public ResponseEntity<?> pwChangeByEmail(@RequestBody Map<String, String> map)
    {
        String email = map.get("email");
        String password = map.get("password");
        String flag = memberService.changePassword(email, password)?"성공!":"실패!";
        log.info(email);
        log.info("변경 비밀번호: {}", password);
        CommonResDto<Boolean> commonResDto = new CommonResDto<>(HttpStatus.OK, "비밀번호 변경 완료!",flag);
        return new ResponseEntity<>(commonResDto,HttpStatus.OK);
    }
    // 멤버 아이디와 변경된 비밀번호로 비밀번호 변경
    @Transactional
    @PutMapping("/changeInfoById")
    @ResponseBody
    public ResponseEntity<?> changeInfoById(@RequestBody Map<String, String> map)
    {
        int id = Integer.parseInt(map.get("id"));
        boolean flag1 = true;
        boolean flag2 = true;

        if(map.containsKey("newPw")){
          flag1 = memberService.changePasswordById(id, map.get("newPw"));
        }
        if(map.containsKey("newNick")){
          flag2 = memberService.changeNickNameById(id, map.get("newNick"));
        }

        CommonResDto<Boolean> commonResDto = new CommonResDto<>(HttpStatus.OK, "member 정보 변경 완료!", flag1 && flag2);
        return new ResponseEntity<>(commonResDto,HttpStatus.OK);
    }
    // 회원 가입 요청
    @PostMapping("/sign-up")
    public ResponseEntity<?> sign_up(@Valid MemberSignUpRequest dto) throws IOException {
        log.info("member/sign-up: Post , dto: {}", dto.toString());
        Member member = memberService.signUp(dto);
        CommonResDto<Integer> resDto =
                new CommonResDto<>(HttpStatus.CREATED, "member create 성공", member.getId());

        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }

    // 이메일, 닉네임 중복 검사
    @PostMapping("/duplicateTest")
    @ResponseBody
    public ResponseEntity<?> check(@RequestBody Map<String, Object> params) {

        log.info("type: {}", params.get("type"));
        log.info("keyword: {}", params.get("keyword"));

        boolean flag = memberService.duplicateTest((String) params.get("type"), (String) params.get("keyword"));
        return ResponseEntity.ok()
                .body(flag);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest dto) {
        // email, password가 맞는 지 검증
        LoginResponse loginResponse = memberService.login(dto);
        CommonResDto<LoginResponse> resDto
                = new CommonResDto<>(HttpStatus.OK, "SUCCESS", loginResponse);
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    // access token이 만료되어 새 토큰을 요청
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        try {
            String newAccessToken = authService.refreshAccessToken(request.memberId());
            return ResponseEntity.ok(new CommonResDto(HttpStatus.OK, "새 토큰 발급됨!", new RefreshResponse(newAccessToken)));
        } catch (ExpiredRefreshTokenException e) {
            //이거 다시 처리해야함.
            if (e.loginMethod() == Member.LoginMethod.KAKAO) {
                // 카카오 로그아웃 요청 (Event로 처리해도 좋음)
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new CommonErrorDto(HttpStatus.UNAUTHORIZED, "EXPIRED_RT"));
        }
    }

    @PostMapping("/email")
    @ResponseBody
    public ResponseEntity<CommonResDto<String>> mailCheck(@RequestBody String email) {
        try {
            String authNum = memberService.verifyAndSendAuthCode(email);
            return ResponseEntity.ok(new CommonResDto<>(HttpStatus.OK, "인증 번호 전송 완료", authNum));
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CommonResDto<>(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다.", ""));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CommonResDto<>(HttpStatus.BAD_REQUEST, "이메일 전송 실패", ""));
        }
    }

    @GetMapping("/getMemberById/{id}")
    public ResponseEntity<?> getMemberById(@PathVariable("id") int id) {
       Member member = memberService.findById(id);
       LoginResponse dto = new LoginResponse(member);
       CommonResDto<Member> resDto
               = new CommonResDto<>(HttpStatus.OK,"멤버 찾았음",member);
       return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

}