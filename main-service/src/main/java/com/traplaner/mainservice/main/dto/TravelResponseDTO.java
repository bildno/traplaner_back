package com.traplaner.mainservice.main.dto;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TravelResponseDTO {
    private Integer id;
    private String title;
    private String travelImg;
}
