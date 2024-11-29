package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TravelBoardDetailResponseDTO {
    private Integer travelId; // t
    private Integer boardId; // tb
    private String title; // t
    private String writer; // m
    private LocalDateTime writeDate; // tb
    private String img; // t
    private String content; // tb
    private Long likeCount; // f
    private List<JourneyResponseDTO> journeys; // j

    @Getter @Setter @ToString
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class JourneyResponseDTO {
        private int day;
        private String journeyName;
        private String placeName;
        private LocalDateTime journeyStartTime;
        private String locationPin;
        private String journeyImg;
    }
}





