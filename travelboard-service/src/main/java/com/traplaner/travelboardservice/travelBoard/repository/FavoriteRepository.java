package com.traplaner.travelboardservice.travelBoard.repository;

import com.traplaner.travelBoard.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
