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
    private Integer travelId;
    private Integer boardId;
    private String title;
    private String writer;
    private LocalDateTime writeDate;
    private String img;
    private String content;
    private Long likeCount;
    private List<JourneyResponseDTO> journeys;

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

/*    public TravelBoardDetailResponseDTO(Member member, Travel travel, TravelBoard travelBoard, Journey journey, int likeCount) {
        this.travelId = travel.getId();
        this.title = travel.getTitle();
        this.writer = member.getNickName();
        this.writeDate = TravelBoardListResponseDTO.makePrettierDateString(travelBoard.getWriteDate());
        this.img = travel.getTravelImg();
        this.content = travelBoard.getContent();
        this.likeCount = likeCount;
    }*/

}





