package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class JourneyDTO {
    private Integer travelId;
    private String journeyName;
    private String accommodationName;
    private Integer day;
    private LocalTime startTime;
    private String googleMapLocationPinInformation;
    private String journeyImg;
}
