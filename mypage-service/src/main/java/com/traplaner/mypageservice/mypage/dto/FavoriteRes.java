package com.traplaner.mypageservice.mypage.dto;

import com.traplaner.mypageservice.mypage.entity.TravelBoard;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteRes {

        private Integer id;
        private Integer memberId;
        private Integer travelBoardId;


}
