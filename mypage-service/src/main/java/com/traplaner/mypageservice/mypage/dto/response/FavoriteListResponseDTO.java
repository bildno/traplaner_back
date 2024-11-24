package com.traplaner.mypageservice.mypage.dto.response;


import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteListResponseDTO {

    private int id;
    private int memberId;
    private int travelBoardId;
    private String memberNickName;
    private LocalDateTime writeDate;
    private String title;
    private String formatDate;

    public static String makeDateStringFomatter(LocalDateTime startDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dtf.format(startDate);
    }

}
