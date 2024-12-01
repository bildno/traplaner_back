package com.traplaner.travelboardservice.travelBoard.repository;

import com.traplaner.travelboardservice.travelBoard.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    @Query("SELECT f.travelBoardId AS travelBoardId, COUNT(f.memberId) AS likeCount " +
            "FROM Favorite f " +
            "GROUP BY f.travelBoardId " +
            "ORDER BY COUNT(f.memberId) DESC " +
            "LIMIT 3")
    List<Map<String, Object>> findTopThreeTravelBoards();
}
