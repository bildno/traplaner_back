package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserResponseDTO {
    private Integer id;
    private String nickName;
}