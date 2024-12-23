package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.common.auth.TokenUserInfo;
import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.FavoriteDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.FavoriteResDTO;
import com.traplaner.travelboardservice.travelBoard.entity.Favorite;
import com.traplaner.travelboardservice.travelBoard.repository.FavoriteRepository;
import com.traplaner.travelboardservice.travelBoard.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Long likeCount = favoriteService.toggleLike(boardId, memberId);

        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "좋아요를 눌렀습니다.", likeCount);

        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

    @GetMapping("/toggle-like/status/{boardId}")
    @ResponseBody
    public ResponseEntity<?> checkLikeStatus(@AuthenticationPrincipal TokenUserInfo userInfo,
                                             @PathVariable Integer boardId) {
        int memberId = Integer.parseInt(userInfo.getId());
        boolean isLiked = favoriteService.isLiked(boardId, memberId);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "좋아요를 누른 상태입니다.", isLiked);

        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

    @GetMapping("/my-favoriteList/{id}")
    public ResponseEntity<?> getMyFavorites(@PathVariable("id") TokenUserInfo userInfo, Pageable pageable) {
        int memberId = Integer.parseInt(userInfo.getId());
        Page<Favorite> myFavorites = favoriteService.myFavorites(memberId, pageable);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "내가 좋아요한 게시물 조회 완료!", myFavorites);
        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

    @PostMapping("/deleteFavorite")
    public ResponseEntity<?> deleteFavorite(Integer travelBoardId) {
        favoriteService.deleteFavorite(travelBoardId);

        return ResponseEntity.ok().build();
    }

}