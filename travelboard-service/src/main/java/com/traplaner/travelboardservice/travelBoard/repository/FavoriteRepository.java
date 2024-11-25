package com.traplaner.travelboardservice.travelBoard.repository;

import com.traplaner.travelboardservice.travelBoard.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {

}
