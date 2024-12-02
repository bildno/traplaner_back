package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardInfoDTO;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardListDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.MemberDTO;
import com.traplaner.travelboardservice.travelBoard.service.FavoriteService;
import com.traplaner.travelboardservice.travelBoard.service.TravelBoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travelboard")
@RequiredArgsConstructor
@Slf4j
public class TravelBoardController {
    private final TravelBoardService travelBoardService;
    private final FavoriteService favoriteService;

    @GetMapping("/list")
    public ResponseEntity<Page<TravelBoardListDTO>> getTravelBoardList(Pageable pageable) {
        return ResponseEntity.ok(travelBoardService.getTravelBoardList(pageable));
    }

    @GetMapping("/info/{boardId}")
    public ResponseEntity<TravelBoardInfoDTO> getTravelBoardInfo(@PathVariable("boardId") Integer boardId) {
        return ResponseEntity.ok(travelBoardService.getTravelBoardInfo(boardId));
    }

    @PostMapping("/{boardId}/toggle-like")
    @ResponseBody
    public ResponseEntity<Integer> toggleLike(@PathVariable("boardId") Integer id, HttpSession session) {
        MemberDTO dto = (MemberDTO) session.getAttribute("login");

        int likeCount = favoriteService.toggleLike(id, dto.getId());

        return ResponseEntity.ok(likeCount);
    }
}
