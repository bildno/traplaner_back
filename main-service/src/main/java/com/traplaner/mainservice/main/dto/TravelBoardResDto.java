package com.traplaner.mainservice.main.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelBoardResDto {
    private int id;
    private int travelId;
    private String memberNickName;
    private String writeDate;
    private String content;
}
