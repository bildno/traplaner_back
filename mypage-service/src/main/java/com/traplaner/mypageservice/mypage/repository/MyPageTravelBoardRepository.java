package com.traplaner.mypageservice.mypage.repository;

import com.traplaner.mypage.dto.response.TravelBoardResponseDTO;
import com.traplaner.travelBoard.entity.TravelBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyPageTravelBoardRepository extends JpaRepository<TravelBoard, Integer> {

    @Query(value = "select" +
            " tb.id, " +
            "tb.travel.id, " +
            "tb.memberNickName, " +
            "DATE_FORMAT(tb.writeDate, '%Y-%m-%d') as write_date " +
            "from TravelBoard tb join Travel t on tb.travel.id  = t.id " +
            "where tb.memberNickName = ?1 and t.share = true")
    Page<TravelBoardResponseDTO> findByMemberNickName(String nickName, Pageable pageable);


    Long countById(long travelId);
}
