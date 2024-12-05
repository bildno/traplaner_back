package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.common.auth.TokenUserInfo;
import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.FavoriteDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.FavoriteResDTO;
import com.traplaner.travelboardservice.travelBoard.entity.Favorite;
import com.traplaner.travelboardservice.travelBoard.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/top3-favorite")
    public ResponseEntity<List<FavoriteResDTO>> getTop3Favorites() {
        List<FavoriteResDTO> top3Favorites = favoriteService.getTopThree();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "top3 좋아요 조회 완료!", top3Favorites);
        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

    @PostMapping("/toggle-like/{boardId}")
    @ResponseBody
    public ResponseEntity<?> toggleLike(@AuthenticationPrincipal TokenUserInfo userInfo,
                                        @PathVariable Integer boardId) {

        int memberId = Integer.parseInt(userInfo.getId());
        Long likeCount = favoriteService.toggleLike(boardId,memberId);

        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "좋아요를 눌렀습니다.", likeCount);

        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

    @GetMapping("/my-favoriteList/{id}")
    public ResponseEntity<?> getMyFavorites(@PathVariable int id, Pageable pageable) {
        Page<Favorite> myFavorites = favoriteService.myFavorites(id, pageable);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "내가 좋아요한 게시물 조회 완료!", myFavorites);
        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

}