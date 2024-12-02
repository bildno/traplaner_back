package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TravelBoardListDTO {
    private Integer travelId; // t
    private Integer boardId; // tb
    private String img; // t
    private String title; // t
    private String writer; // m
    private LocalDateTime writeDate; // tb
    private Long likeCount; // f
}
