package com.traplaner.travelboardservice.travelBoard.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteResDTO {
    private Integer travelBoardId;
    private Long likeCount;
}
