package com.traplaner.travelboardservice.travelBoard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TravelBoardInfoDTO {

    private Integer travelId; // tb
    private Integer boardId; // tb
    private String title; // t
    private String nickName; // m

/*    @JsonFormat(pattern = "yyyy-MM-dd 'T'HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)*/
    private String writeDate; // tb
    private String travelImg; // t
    private String content; // tb
    private Long likeCount; // f
    private List<JourneyInfoDTO> journeys; // j

    public TravelBoardInfoDTO(Integer id, String title, String nickName, String writeDate, String travelImg, String content, long likeCount, List<JourneyInfoDTO> journeyDetails) {
        this.travelId = id;
        this.title = title;
        this.nickName = nickName;
        this.writeDate = writeDate;
        this.travelImg = travelImg;
        this.content = content;
        this.likeCount = likeCount;
        this.journeys = journeyDetails;
    }

    @Getter @Setter @ToString
    @NoArgsConstructor @AllArgsConstructor
    @Builder
    public static class JourneyInfoDTO {
        private Integer travelId;
        private String journeyName;
        private String accommodationName;
        private int day;

        @JsonFormat(pattern = "yyyy-MM-dd 'T'HH:mm")
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime startTime;
        private String googleMapLocationPinInformation;
        private String journeyImg;

        public JourneyInfoDTO(String journeyName, String accommodationName, int day, LocalDateTime startTime, String googleMapLocationPinInformation, String journeyImg) {
            this.journeyName = journeyName;
            this.accommodationName = accommodationName;
            this.day = day;
            this.startTime = startTime;
            this.googleMapLocationPinInformation = googleMapLocationPinInformation;
            this.journeyImg = journeyImg;
        }
    }
}





