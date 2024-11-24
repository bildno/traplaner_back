package com.traplaner.travelboardservice.travelBoard.repository;

import com.traplaner.travelBoard.entity.TravelBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelBoardRepository extends JpaRepository<TravelBoard,Long> {

}
