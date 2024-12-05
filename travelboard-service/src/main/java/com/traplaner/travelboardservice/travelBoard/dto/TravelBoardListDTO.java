package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TravelBoardListDTO {
    private Integer id; // tb
    private Integer travelId; // tb
    private String travelImg; // t
    private String title; // t
    private String nickName; // m
    private String writeDate; // tb
    private Long likeCount; // f
}
