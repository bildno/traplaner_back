package com.traplaner.travelboardservice.travelBoard.dto;

import com.traplaner.member.entity.Member;
import com.traplaner.travelBoard.entity.TravelBoard;
import com.project.traplaner.travelplan.entity.Journey;
import com.project.traplaner.travelplan.entity.Travel;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TravelBoardDetailResponseDTO {
    private int travelId;
    private int boardId;
    private String title;
    private String writer;
    private String writeDate;
    private String img;
    private String content;
    private int likeCount;

    public TravelBoardDetailResponseDTO(Member member, Travel travel, TravelBoard travelBoard, Journey journey, int likeCount) {
        this.travelId = travel.getId();
        this.title = travel.getTitle();
        this.writer = member.getNickName();
        this.writeDate = TravelBoardListResponseDTO.makePrettierDateString(travelBoard.getWriteDate());
        this.img = travel.getTravelImg();
        this.content = travelBoard.getContent();
        this.likeCount = likeCount;
    }

}





