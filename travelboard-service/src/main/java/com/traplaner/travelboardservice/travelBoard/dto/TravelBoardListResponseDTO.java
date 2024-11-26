package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TravelBoardListResponseDTO {
    private Integer travelId;
    private Integer boardId;
    private String img;
    private String title;
    private String writer;
    private LocalDateTime writeDate;
    private Long likeCount;

/*    public TravelBoardListResponseDTO(TravelBoardDetailResponseDTO detail) {
        this.shortTitle = makeShortTitle(detail.getTitle());
        this.writeDate = LocalDateTime.parse(makePrettierDateString(detail.getWriteDate()));
    }

    public static String makePrettierDateString(LocalDateTime writeDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy. MM. dd. HH:mm");
        return dtf.format(writeDate);
    }

    private String makeShortTitle(String title) {
        return sliceString(title, 8);
    }

    private String sliceString(String target, int wishLength) {
        return (target.length() > wishLength)
                ? target.substring(0, wishLength) + "..."
                : target;
    }*/
}
