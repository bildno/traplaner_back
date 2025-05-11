package com.traplaner.memberservice.member.service;

import com.traplaner.memberservice.member.controller.request.MemberSignUpRequest;
import com.traplaner.memberservice.member.domain.Member;
import com.traplaner.memberservice.member.mock.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceTest {
    private MemberServiceImpl memberService;
    private MailSenderService mailSenderService;

    @BeforeEach
    void setUp() {
        FakeFileUploader fakeFileUploader = new FakeFileUploader();
        FakeMemberRepository fakeMemberRepository = new FakeMemberRepository();
        this.memberService = MemberServiceImpl.builder()
                .encoder(new FakePasswordEncoder())
                .fileUploader(new FakeFileUploader())
                .mailSenderService(mailSenderService)
                .memberRepository(new FakeMemberRepository())
                .refreshTokenRepository(new FakeRefreshTokenRepository())
                .tokenProvider(new FakeTokenProvider())
                .build();
    }

    @Test
    void MemberSignUpRequest로_회원가입을_할_수_있다(){
        //given
        FakeMultipartfle fakeMultipartfle = new FakeMultipartfle();
        MemberSignUpRequest request = MemberSignUpRequest.builder()
                .email("test@test.com")
                .password("123456")
                .profileImage(fakeMultipartfle)
                .nickName("test")
                .build();
        //when
        Member member = memberService.signUp(request);
        //then
        assertThat(member.getEmail()).isEqualTo("test@test.com");
        assertThat(member.getNickName()).isEqualTo("test");
        //비밀번호 확인 로직도 돌아가면 좋을꺼 같은데.
    }

}