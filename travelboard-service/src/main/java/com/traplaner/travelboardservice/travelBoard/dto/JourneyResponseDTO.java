package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JourneyResponseDTO {
    private Integer id;
    private Integer travelId;
    private String journeyName;
    private String accommodationRoadAddressName;
    private int day;
    private LocalDateTime startTime;
    private String googleMapLocationPinInformation;
    private String journeyImg;
}
