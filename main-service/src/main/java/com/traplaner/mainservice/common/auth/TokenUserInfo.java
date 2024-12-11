package com.traplaner.mainservice.common.auth;


import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenUserInfo {

    private String memberId;

}
