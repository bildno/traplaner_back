package com.traplaner.travelplanservice.travelplan.repository;

import com.traplaner.travelplanservice.travelplan.entity.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyRepository extends JpaRepository<Journey, Integer> {

    Optional<Journey> findByTravelId(int travelId);
}
