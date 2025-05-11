package com.traplaner.memberservice.member.service;
import com.traplaner.memberservice.member.controller.port.MemberService;
import com.traplaner.memberservice.member.controller.request.MemberSignUpRequest;
import com.traplaner.memberservice.common.exception.MemberNotFoundException;
import com.traplaner.memberservice.member.domain.LoginRequest;
import com.traplaner.memberservice.member.domain.LoginResponse;
import com.traplaner.memberservice.member.domain.Member;
import com.traplaner.memberservice.member.domain.MemberCreate;
import com.traplaner.memberservice.member.service.port.FileUploader;
import com.traplaner.memberservice.member.service.port.MemberRepository;
import com.traplaner.memberservice.member.service.port.RefreshTokenRepository;
import com.traplaner.memberservice.member.service.port.TokenProvider;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
@Builder
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder encoder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final FileUploader fileUploader;
    private final MailSenderService mailSenderService;

    @Override
    public Member signUp(MemberSignUpRequest request) {
        String imageUrl = fileUploader.uploadtos3bucket(request.getProfileImage());
        MemberCreate memberCreate = request.toMemberCreate(imageUrl);
        memberCreate.setLoginMethod(Member.LoginMethod.COMMON);
        // e:파일 업로드 -------------------------
        return memberRepository.save(memberCreate.toModel());
    }

    public boolean changePassword(String email, String password) {

        Member foundMember = memberRepository.findByEmail(email).orElseThrow(()->
                new EntityNotFoundException("비밀번호 변경 실패! 그런 이메일은 없습니다.")
                );
        foundMember.changePassword(encoder.encode(password));
        memberRepository.save(foundMember);
        return true;
    }

    public boolean changePasswordById(int id, String password) {

        Member foundMember = memberRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("비밀번호 변경 실패! 그런 아이디는 없습니다.")
        );
        foundMember = foundMember.changePassword(encoder.encode(password));
        memberRepository.save(foundMember);
        return true;
    }

    public LoginResponse login(LoginRequest dto) {
        Member member = memberRepository.findByEmail(dto.getEmail()).orElseThrow(() ->
                new EntityNotFoundException("NO_ACC")
        );
        // 비밀번호 확인하기 (암호화 되어있으니 encoder에게 부탁)
        // 외부 종속성이 도메인에 위치하면 곤란하므로 서비스에 짧은 로직으로 추가
        if (!encoder.matches(dto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("NO_PW");
        }
        String accessToken = tokenProvider.createToken(member.getId());
        String refreshToken
                = tokenProvider.createRefreshToken(member.getEmail());
        // refresh Token을 DB에 저장하자. -> redis에 저장.
          refreshTokenRepository.save(member.getEmail(),accessToken,240,TimeUnit.HOURS);
          return LoginResponse.from(member, accessToken);
    }

    public Member findById(int id) {
        return memberRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Member not found")
        );
    }

    public boolean duplicateTest(String type, String keyword) {
        return memberRepository.duplicateTest(type,keyword);
    }

    public boolean changeNickNameById(int id, String nickName) {
        Member member = memberRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("닉네임 변경 실패");
        });
        member = member.changeNickName(nickName);
        memberRepository.save(member);
        return true;
    }

    public String verifyAndSendAuthCode(String email) throws MessagingException {
        if (duplicateTest("email",email)) {
            throw new MemberNotFoundException(email);
        }
        return mailSenderService.joinMail(email);
    }
}
