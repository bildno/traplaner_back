package com.traplaner.mypageservice.mypage.repository;

import com.traplaner.mypageservice.mypage.entity.TravelBoard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TravelBoardRepository extends JpaRepository<TravelBoard, Integer> {

    boolean existsByTravelId(int travelId);
}
