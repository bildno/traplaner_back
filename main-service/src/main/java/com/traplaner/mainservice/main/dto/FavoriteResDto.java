package com.traplaner.mainservice.main.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResDto {
    private int id;
    private int memberId;
    private int travelBoardId;
}
