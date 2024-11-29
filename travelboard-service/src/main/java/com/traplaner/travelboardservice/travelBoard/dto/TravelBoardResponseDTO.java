package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelBoardResponseDTO {
    private Integer id;
    private Integer travelId;
    private LocalDateTime writeDate;
    private String content;
}
