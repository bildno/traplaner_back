package com.traplaner.travelplanservice.travelplan.dto;

import com.traplaner.travelplanservice.travelplan.entity.Travel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    public TravelResponseDTO(Travel travel) {
        travelImg = travel.getTravelImg();
        title = travel.getTitle();
        id = travel.getId();
    }
}
