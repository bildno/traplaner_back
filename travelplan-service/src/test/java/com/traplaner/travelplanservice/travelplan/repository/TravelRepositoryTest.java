package com.traplaner.travelplanservice.travelplan.repository;
import com.traplaner.travelplanservice.travelplan.entity.Journey;
import com.traplaner.travelplanservice.travelplan.entity.Travel;
import jakarta.persistence.EntityNotFoundException;
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

        int subscriberCount = 250;
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
        // 여정 더미 데이터 생성
        for (int i = 1; i <= subscriberCount; i++) {
            int randNum = (int) (Math.random() * randomPara) + 1;
            int tmpTravelId = randNum;
            Travel travel = travelRepository.findById(tmpTravelId).orElseThrow(
                    ()->new EntityNotFoundException("Travel not found")
            );
            LocalDateTime tmpStartTime = travel.getStartDate();
            LocalDateTime tmpEndTime = tmpStartTime.plusHours(3);

            String fileName = String.format("0425d9dc324e4d2a822b8ac905123b%02d.jpg", (int) (Math.random() * 9) + 1);

            String[] journeyNameArr = {"아침 식사","동물원 탐방","점심 식사","놀이 공원","저녁 식사"};
            String[] accomodationNameArr = {"스타 벅스", "서울 대공원", "정식당","롯데월드","고든램지버거"};
            String[] accomodationAdressArr = {"서울특별시 강남구 강남대로 328","경기도 과천시 대공원광장로 102"
                    ,"서울특별시 강남구 선릉로158길 11", "서울특별시 송파구 올림픽로 240"
                    ,"서울특별시 송파구 올림픽로 300 롯데월드몰 B1"};

            Journey journey = Journey.builder()
                    .travelId(tmpTravelId)
                    .journeyName(journeyNameArr[i%5])
                    .accommodationName(accomodationNameArr[i%5])
                    .accommodationRoadAddressName(accomodationAdressArr[i%5])
                    .day(1)
                    .startTime(tmpStartTime)
                    .endTime(tmpEndTime)
                    .googleMapLocationPinInformation("testGoogleMapLocationPinInfo")
                    .reservationConfirmImagePath(fileName)
                    .share(true)
                    .build();
            journeyRepository.save(journey);

        }


    }
}