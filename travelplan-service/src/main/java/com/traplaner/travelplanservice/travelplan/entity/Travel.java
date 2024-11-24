package com.traplaner.travelplanservice.travelplan.entity;

import com.traplaner.common.entity.BaseTimeEntity;
import com.project.traplaner.member.entity.Member;
import com.project.traplaner.travelBoard.entity.TravelBoard;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_travel")
public class Travel extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    //이거 필요한가?
//    @Column(name = "is_domestic")
//    private boolean isDomestic;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    private boolean share;

    @Column(name = "travel_img")
    private String travelImg;

    // by jhjeong 11.20
    @OneToOne(mappedBy="travel", fetch = FetchType.LAZY)
    private TravelBoard travelBoard;
}
