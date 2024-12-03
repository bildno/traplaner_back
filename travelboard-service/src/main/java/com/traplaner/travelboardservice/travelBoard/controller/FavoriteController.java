package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.common.auth.TokenUserInfo;
import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.response.FavoriteResDTO;
import com.traplaner.travelboardservice.travelBoard.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<?> toggleLike(@PathVariable Integer boardId,
                                        @AuthenticationPrincipal TokenUserInfo userInfo) {
        int memberId = Integer.parseInt(userInfo.getId());
        Long likeCount = favoriteService.toggleLike(boardId);

        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "좋아요를 눌렀습니다.", likeCount);

        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

}
