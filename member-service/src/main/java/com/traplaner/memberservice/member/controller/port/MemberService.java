package com.traplaner.memberservice.member.controller.port;

import com.traplaner.memberservice.member.controller.request.MemberSignUpRequest;
import com.traplaner.memberservice.member.domain.LoginRequest;
import com.traplaner.memberservice.member.domain.LoginResponse;
import com.traplaner.memberservice.member.domain.Member;
import jakarta.mail.MessagingException;

public interface MemberService {
    Member signUp(MemberSignUpRequest request);

    boolean changePassword(String email, String password);

    boolean changePasswordById(int id, String newPw);

    boolean changeNickNameById(int id, String newNick);

    boolean duplicateTest(String type, String keyword);

    LoginResponse login(LoginRequest dto);

    String verifyAndSendAuthCode(String email) throws MessagingException;

    Member findById(int id);
}
