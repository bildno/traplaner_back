package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteDTO {
    private Integer travelBoardId;
    private Long likeCount;
}
