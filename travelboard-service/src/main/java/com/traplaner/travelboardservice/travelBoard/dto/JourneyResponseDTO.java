package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class JourneyResponseDTO {
    private String journeyName;
    private String placeName;
    private LocalDateTime journeyStartTime;
    private String locationPin;
    private String journeyImg;

    public JourneyResponseDTO(Journey journey) {
        this.journeyName = journey.getJourneyName();
        this.placeName = journey.getAccommodationName();

        this.journeyStartTime = journey.getStartTime();

        this.locationPin = journey.getGoogleMapLocationPinInformation();
    }
}
