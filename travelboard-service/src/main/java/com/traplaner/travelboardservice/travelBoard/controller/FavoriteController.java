package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.response.FavoriteResDTO;
import com.traplaner.travelboardservice.travelBoard.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/top3-favorite")
    public ResponseEntity<List<FavoriteResDTO>> getTopThreeFavorites() {
        List<FavoriteResDTO> topThreeFavorites = favoriteService.getTopThree();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "top3 좋아요 조회 완료!", topThreeFavorites);
        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

}