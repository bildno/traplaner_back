package com.traplaner.travelboardservice.travelBoard.entity;
import com.traplaner.member.entity.Member;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;                  // by jhjeong 11.21

    @ManyToOne(fetch = FetchType.LAZY)      // by jhjeong 11.21
    @JoinColumn(name = "travel_board_id", nullable = false)
    private TravelBoard travelBoard;        // by jhjeong 11.21
}