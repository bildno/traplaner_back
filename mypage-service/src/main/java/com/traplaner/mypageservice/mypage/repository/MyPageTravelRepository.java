package com.traplaner.mypageservice.mypage.repository;

import com.traplaner.travelplan.entity.Travel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPageTravelRepository extends JpaRepository<Travel, Integer> {

    List<Travel> findByMemberId(int id);

    Page<Travel> findByMemberId(int id, Pageable pageable);


}
