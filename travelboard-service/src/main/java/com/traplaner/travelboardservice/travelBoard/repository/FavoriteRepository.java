package com.traplaner.travelboardservice.travelBoard.repository;

import com.traplaner.travelboardservice.travelBoard.dto.FavoriteDTO;
import com.traplaner.travelboardservice.travelBoard.entity.Favorite;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    @Query("SELECT f.travelBoardId AS travelBoardId, COUNT(f.memberId) AS likeCount " +
            "FROM Favorite f " +
            "GROUP BY f.travelBoardId " +
            "ORDER BY COUNT(f.memberId) DESC " +
            "LIMIT 3")
    List<Map<String, Object>> findTopThreeTravelBoards();

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Favorite f WHERE f.travelBoardId = :travelBoardId AND f.memberId = :memberId")
    boolean isLikedByMember(@Param("travelBoardId") int travelBoardId, @Param("memberId") int memberId);


    @Modifying
    @Transactional
    @Query("DELETE FROM Favorite f WHERE f.travelBoardId = :travelBoardId AND f.memberId = :memberId")
    void removeLike(@Param("travelBoardId") int travelBoardId, @Param("memberId") int memberId);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tbl_favorite (travel_board_id, member_id) VALUES (:#{#travelBoardId['travelBoardId']}, :#{#travelBoardId['memberId']})", nativeQuery = true)
    void addLike(@Param("travelBoardId") Map<String, Integer> travelBoardId);

    @Query("SELECT COUNT(f.memberId) FROM Favorite f WHERE f.travelBoardId = :travelBoardId")
    Long getLikeCount(@Param("travelBoardId") Integer travelBoardId);

    @Query("SELECT f.travelBoardId FROM Favorite f WHERE f.memberId = :memberId")
    Page<Map<String, Object>> getMyFavorites(@Param("memberId") Integer memberId, Pageable pageable);
}