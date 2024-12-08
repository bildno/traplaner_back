package com.traplaner.travelboardservice.travelBoard.dto.request;

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
