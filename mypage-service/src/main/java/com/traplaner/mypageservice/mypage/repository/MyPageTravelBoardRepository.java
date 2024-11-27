package com.traplaner.mypageservice.mypage.repository;


import com.traplaner.mypageservice.mypage.dto.response.TravelBoardResponseDTO;
import com.traplaner.mypageservice.mypage.entity.TravelBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyPageTravelBoardRepository extends JpaRepository<TravelBoard, Integer> {

    Page<TravelBoardResponseDTO> findByMemberNickName(String nickName, Pageable pageable);


    Long countById(long travelId);
}
