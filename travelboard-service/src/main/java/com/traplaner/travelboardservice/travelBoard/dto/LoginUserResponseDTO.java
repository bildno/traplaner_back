package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString @Builder
public class LoginUserResponseDTO {
    private String nickName;
}