package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TravelDTO {
    private Long id;
    private Long memberId;
    private String title;
    private LocalDate startDate;
    private String travelImg;
}
