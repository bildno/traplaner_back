package com.traplaner.travelplanservice.travelplan.repository;

import com.traplaner.travelplanservice.travelplan.entity.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Integer> {

    Travel findTopByOrderByIdDesc();

    List<Travel> findAllByMemberId(int memberId);

   Page<Travel> findAllByMemberId(int memberId, Pageable pageable);
}
