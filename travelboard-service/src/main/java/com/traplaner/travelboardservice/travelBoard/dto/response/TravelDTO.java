package com.traplaner.travelboardservice.travelBoard.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelDTO {

    private Integer id;
    private String memberId;
    private boolean share;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime endDate;
    private LocalDateTime startDate;
    private String title;
    private String travelImg;


}
