package com.traplaner.travelboardservice.travelBoard.repository;

import com.traplaner.travelboardservice.travelBoard.entity.TravelBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelBoardRepository extends JpaRepository<TravelBoard,Integer> {

}
