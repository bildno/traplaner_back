package com.traplaner.mainservice.main.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TravelResDto {
    private int id;
    private int memberId;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean share;
    private String travelImg;
}
