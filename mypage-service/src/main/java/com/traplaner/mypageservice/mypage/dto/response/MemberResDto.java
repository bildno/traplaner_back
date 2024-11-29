package com.traplaner.mypageservice.mypage.dto.response;


import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResDto {

    private Integer id;
    private String email;
    private String nickName;
    private String profileImg;
    private String password;





}
