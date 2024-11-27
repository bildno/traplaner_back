package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TravelBoardListResponseDTO {
    private Integer travelId;
    private Integer boardId;
    private String img;
    private String title;
    private String writer;
    private LocalDateTime writeDate;
    private Long likeCount;
}
