package com.traplaner.travelplanservice.travelplan.repository;

import com.traplaner.travelplanservice.travelplan.entity.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JourneyRepository extends JpaRepository<Journey, Integer> {

    Optional<Journey> findByTravelId(int travelId);

    List<Journey> findAllByTravelId(int travelId);

    List<Journey> findAllByTravelIdOrderByStartTimeAsc(int travelId);
}
