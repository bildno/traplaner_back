package com.traplaner.travelplanservice.travelplan.dto;


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
    private int memberId;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean share;
    private String travelImg;
}
