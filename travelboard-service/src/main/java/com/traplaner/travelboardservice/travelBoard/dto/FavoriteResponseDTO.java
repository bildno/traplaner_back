package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponseDTO {
    private Integer id;
    private Integer memberId;
    private Integer travelBoardId;
}
