package com.traplaner.travelboardservice.travelBoard.repository;

import com.traplaner.travelboardservice.travelBoard.entity.TravelBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Rollback(false)
class TravelBoardRepositoryTest {


    @Autowired
    TravelBoardRepository travelBoardRepository;

    @Test
    @DisplayName("Test DB 테이블 데이터 생성")
    void makeTestTableData() {

        int subscriberCount = 100;
        int randomPara = subscriberCount - 1;
        int favoriteCount = subscriberCount;
        int randomFavoritePara = (subscriberCount / 10) - 1;

        //여행 게시판 더미 데이터 생성
        for (int i = 1; i <= subscriberCount; i++) {
            int randNum = (int) (Math.random() * randomPara) + 1;
            int tmpMemberId = randNum;
            int tmpMemberNickNameId = randNum;

            LocalDateTime tmpStartDate = LocalDateTime.now();
            LocalDateTime tmpEndDate = LocalDateTime.now().plusDays(3);
            LocalDateTime tmpUpdatedDate =
                    LocalDateTime.now().plusDays(3 + (int) (Math.random() * randomPara) + 1);

            TravelBoard travelBoard = TravelBoard.builder()
                    .travelId(tmpMemberId)
                    .memberNickName("테스트" + tmpMemberNickNameId)
                    .writeDate(tmpEndDate)
                    .content("여행-" + i + " 좋았음. !!!!!!!!!!!!")
                    .build();
            travelBoardRepository.save(travelBoard);
        }


    }
}