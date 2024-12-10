package com.traplaner.mypageservice.mypage.repository;


import com.traplaner.mypageservice.mypage.dto.response.TravelBoardResponseDTO;
import com.traplaner.mypageservice.mypage.entity.TravelBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyPageTravelBoardRepository extends JpaRepository<TravelBoard, Integer> {


    @Query(value = "select t from TravelBoard t where t.memberNickName = ?1")
    Page<TravelBoard> findByMemberNickName(String nickName, Pageable pageable);


    Long countByTravelId(int travelId);


    Optional<TravelBoard> findByTravelId(int travelNo);

    List<TravelBoard> findByIdIn(List<Integer> boardIds);
}
