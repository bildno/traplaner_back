package com.traplaner.travelboardservice.travelBoard.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true) // 알 수 없는 필드 무시
public class TravelBoardDTO {
    private Integer id;
    private Integer travelId;
    private String writeDate;
    private String content;
}
