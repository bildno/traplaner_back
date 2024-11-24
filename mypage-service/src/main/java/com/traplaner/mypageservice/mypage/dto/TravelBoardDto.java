package com.traplaner.mypageservice.mypage.dto;

import com.traplaner.travelBoard.entity.TravelBoard;
import com.traplaner.travelplan.entity.Travel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelBoardDto {
    
    private Travel travel;
    private String memberNickName;
    private LocalDateTime writeDate;
    private String content;


    public TravelBoard toEntity() {
        return TravelBoard.builder()
                .travel(travel)
                .memberNickName(memberNickName)
                .writeDate(writeDate).content(content).build();
    }

}
