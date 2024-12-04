package com.traplaner.travelboardservice.travelBoard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

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

    public TravelBoardListDTO(Integer id, String travelImg, String title, String nickName, String writeDate, Long likeCount) {
        this.travelId = id;
        this.travelImg = travelImg;
        this.title = title;
        this.nickName = nickName;
        this.writeDate = writeDate;
        this.likeCount = likeCount;
    }
}
