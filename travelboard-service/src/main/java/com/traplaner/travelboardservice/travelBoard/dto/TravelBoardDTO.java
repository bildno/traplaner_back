package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TravelBoardDTO {
    private Integer id;
    private Integer travelId;
    private LocalDateTime writeDate;
    private String content;
}
