package com.traplaner.travelboardservice.travelBoard.repository;

import com.traplaner.travelboardservice.travelBoard.entity.Favorite;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    @DisplayName("Test DB 테이블 데이터 생성")
    void makeTestTableData() {

        int subscriberCount = 100;
        int randomPara = subscriberCount - 1;
        int favoriteCount = subscriberCount;
        int randomFavoritePara = (subscriberCount / 10) - 1;


        for (int i = 1; i <= favoriteCount; i++) {
            int tmpFavoriteMemberId = (int) (Math.random() * randomFavoritePara) + 1;
            int tmpFavoriteTravelBoardId = (int) (Math.random() * randomFavoritePara) + 1;

            Favorite favorite = Favorite.builder()
                    .memberId(tmpFavoriteMemberId)
                    .travelBoardId(tmpFavoriteTravelBoardId)
                    .build();

            favoriteRepository.save(favorite);
        }

    }
}