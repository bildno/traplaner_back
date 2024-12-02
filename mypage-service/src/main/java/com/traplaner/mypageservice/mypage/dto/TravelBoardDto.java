package com.traplaner.mypageservice.mypage.dto;

import com.traplaner.mypageservice.mypage.entity.TravelBoard;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelBoardDto {
    
    private Integer travelId;
    private String memberNickName;
    private LocalDateTime writeDate;
    private String content;


    public TravelBoard toEntity() {
        return TravelBoard.builder()
                .travelId(travelId)
                .memberNickName(memberNickName)
                .writeDate(writeDate).content(content).build();
    }

}
