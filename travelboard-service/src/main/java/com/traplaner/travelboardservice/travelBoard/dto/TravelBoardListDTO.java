package com.traplaner.travelboardservice.travelBoard.dto;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TravelBoardListDTO {
    private Integer boardId; // tb
    private Integer travelId; // tb
    private String travelImg; // t
    private String title; // t
    private String nickName; // m

/*    @JsonFormat(pattern = "yyyy-MM-dd 'T'HH:mm")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)*/
    private String writeDate; // tb
    private Long likeCount; // f

}
