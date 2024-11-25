package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString @Builder
public class LoginUserResponseDTO {
    private int id;
    private String nickName;
    private String email;
    private String loginMethod;
    private String profile;

}