package com.traplaner.mypageservice.mypage.dto;


import com.traplaner.mypageservice.mypage.dto.response.MemberResDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class ModifyMemberInfoDTO {

    private int id;
    private String newPw;
    private String newNick;


    // dto를 Entity로 바꾸는 유틸메서드
    public MemberResDto toEntity(PasswordEncoder encoder) {
        return MemberResDto.builder()
                .id(id)
                // 날것의 비밀번호를 그대로 저장하지 않고, encoder를 이용해 암호화 한 후 세팅
                .password(encoder.encode(newPw))
                .nickName(newNick)
                .build();
    }
}
