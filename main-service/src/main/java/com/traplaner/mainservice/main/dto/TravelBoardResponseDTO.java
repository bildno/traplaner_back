package com.traplaner.mainservice.main.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelBoardResponseDTO {

    private int id;
    private int travelId;
    private String memberNickName;
    private String writeDate;
    private String content;

    private String formatDate;

    public static String makeDateStringFomatter(LocalDateTime writeDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dtf.format(writeDate);

        return format;
    }

}
