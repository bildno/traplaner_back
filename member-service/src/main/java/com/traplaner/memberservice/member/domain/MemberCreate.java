package com.traplaner.memberservice.member.domain;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreate {

    @NotBlank(message = "이메일 필수!")
    @Email
    private String email;

    @NotBlank(message = "비밀번호 필수입니다.")
    private String password;

    @NotBlank
    @Size(min = 2, max = 6)
    private String nickName;

    private String profileImage;

    private Member.LoginMethod loginMethod;

    public Member toModel() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .loginMethod(loginMethod)
                .profileImg(profileImage)
                .build();
    }
}

