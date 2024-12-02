package com.traplaner.mainservice.main.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResDto {
//    private Integer id;
//    private Integer memberId;
    private Integer travelBoardId;
    private Long likeCount;
}
