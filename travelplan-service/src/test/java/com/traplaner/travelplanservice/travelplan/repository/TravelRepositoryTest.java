package com.traplaner.travelplanservice.travelplan.repository;

import com.traplaner.travelplanservice.travelplan.entity.Travel;
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
class TravelRepositoryTest {

    @Autowired
    TravelRepository travelRepository;

    @Autowired
    private JourneyRepository journeyRepository;


    @Test
    @DisplayName("Test DB 테이블 데이터 생성")
    void makeTestTableData() {

        int subscriberCount = 100;
        int randomPara = subscriberCount - 1;
        int favoriteCount = subscriberCount;
        int randomFavoritePara = (subscriberCount / 10) - 1;


        //여행 더미 데이터 생성
        for (int i = 1; i <= subscriberCount; i++) {
            int randNum = (int) (Math.random() * randomPara) + 1;
            int tmpMemberId = randNum;
            int tmpMemberNickNameId = randNum;

            LocalDateTime tmpStartDate = LocalDateTime.now();
            LocalDateTime tmpEndDate = LocalDateTime.now().plusDays(3);
            LocalDateTime tmpUpdatedDate =
                    LocalDateTime.now().plusDays(3 + (int) (Math.random() * randomPara) + 1);

            String fileName = String.format("0425d9dc324e4d2a822b8ac905123b%02d.jpg", (int) (Math.random() * 9) + 1);

            Travel travel = Travel.builder()
                    .memberId(tmpMemberId)
                    .title("여행-" + i)
                    .startDate(tmpStartDate)
                    .endDate(tmpEndDate)
                    .share(true)
//                    .travelImg("\\0425d9dc324e4d2a822b8ac905123b9"
//                            + (int) (Math.random() * 9) + 1
//                            +"1.jpg")
                    .travelImg(fileName)
                    .build();
            travelRepository.save(travel);

        }


    }
}