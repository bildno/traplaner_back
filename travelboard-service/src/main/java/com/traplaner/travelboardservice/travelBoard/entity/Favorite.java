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

    @JoinColumn(nullable = false)
    private int travelBoardId;      // by jhjeong 11.21
}