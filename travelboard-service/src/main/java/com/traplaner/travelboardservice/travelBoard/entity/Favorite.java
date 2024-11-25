package com.traplaner.travelboardservice.travelBoard.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn
    private int memberId;                  // by jhjeong 11.21

    @ManyToOne(fetch = FetchType.LAZY)      // by jhjeong 11.21
    @JoinColumn(name = "travel_board_id", nullable = false)
    private TravelBoard travelBoard;        // by jhjeong 11.21
}