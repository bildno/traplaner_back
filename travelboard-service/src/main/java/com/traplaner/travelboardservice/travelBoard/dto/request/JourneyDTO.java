package com.traplaner.travelboardservice.travelBoard.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyDTO {
    private Integer travelId;
    private String journeyName;
    private String accommodationName;
    private int day;
    private LocalDateTime startTime;
    private String googleMapLocationPinInformation;
    private String journeyImg;
}
