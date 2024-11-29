package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelResponseDTO {
    private Integer id;
    private Integer memberId;
    private String title;
    private LocalDateTime startDate;
    private boolean share;
    private String travelImg;
}
