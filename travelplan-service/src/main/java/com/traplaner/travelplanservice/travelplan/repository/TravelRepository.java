package com.traplaner.travelplanservice.travelplan.repository;

import com.traplaner.travelplanservice.travelplan.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Integer> {

    Travel findTopByOrderByIdDesc();
}
