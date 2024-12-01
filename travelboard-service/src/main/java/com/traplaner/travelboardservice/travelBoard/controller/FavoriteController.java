package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.travelBoard.dto.FavoriteDTO;
import com.traplaner.travelboardservice.travelBoard.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/topThree")
    public ResponseEntity<List<FavoriteDTO>> getTopThreeFavorites() {
        List<FavoriteDTO> topThreeFavorites = favoriteService.getTopThree();
        return ResponseEntity.ok(topThreeFavorites);
    }
}
