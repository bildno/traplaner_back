package com.traplaner.travelplanservice.travelplan.repository;

import com.traplaner.travelplan.entity.Journey;
import com.project.traplaner.travelplan.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JourneyRepository extends JpaRepository<Journey, Long> {

    Optional<Journey> findByTravel(Travel travel);
}
