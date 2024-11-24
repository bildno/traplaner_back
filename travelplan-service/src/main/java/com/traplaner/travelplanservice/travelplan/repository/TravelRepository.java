package com.traplaner.travelplanservice.travelplan.repository;

import com.traplaner.travelplan.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Long> {

}
