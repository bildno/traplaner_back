package com.traplaner.mypageservice.mypage.dto.response;


import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelListResponseDTO {

    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private boolean share;


    public TravelListResponseDTO(travelPlanResDto travel ) {
        this.id = travel.getId();
        this.title = travel.getTitle();
        this.startDate = makeDateStringFomatter((travel.getStartDate()));
        this.endDate = makeDateStringFomatter((travel.getEndDate()));
        this.share = travel.isShare();
    }

    public static String makeDateStringFomatter(LocalDateTime startDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dtf.format(startDate);
    }


}
