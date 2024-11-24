package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TravelBoardListResponseDTO {
    private int travelId;
    private int boardId;
    private String img;
    private String shortTitle;
    private String writer;
    private String writeDate;
    private int likeCount;

    public TravelBoardListResponseDTO(TravelBoardDetailResponseDTO board) {
        this.img = board.getImg();
        this.shortTitle = makeShortTitle(board.getTitle());
        this.writer = board.getWriter();
        this.writeDate = board.getWriteDate();
    }

    public static String makePrettierDateString(LocalDateTime writeDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy. MM. dd.");
        return dtf.format(writeDate);
    }

    private String makeShortTitle(String title) {
        return sliceString(title, 8);
    }

    private String sliceString(String target, int wishLength) {
        return (target.length() > wishLength)
                ? target.substring(0, wishLength) + "..."
                : target;
    }
}
