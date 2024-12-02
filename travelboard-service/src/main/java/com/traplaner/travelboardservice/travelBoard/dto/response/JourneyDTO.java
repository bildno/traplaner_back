package com.traplaner.travelboardservice.travelBoard.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JourneyDTO {
    private Integer travelId;
    private String journeyName;
    private String accommodationName;
    private int day;
    private LocalDateTime startTime;
    private String googleMapLocationPinInformation;
    private String journeyImg;
}
