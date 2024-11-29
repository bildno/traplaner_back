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

        private int id;
        private int memberId;
        private int travelBoardId;


}
