package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardDetailResponseDTO;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardListResponseDTO;
import com.traplaner.travelboardservice.travelBoard.service.TravelBoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travelboard")
@RequiredArgsConstructor
@Slf4j
public class TravelBoardController {
    private final TravelBoardService travelBoardService;

    @GetMapping("/list")
    public ResponseEntity<TravelBoardListResponseDTO> getTravelBoardList() {
        return ResponseEntity.ok(travelBoardService.getTravelBoardList());
    }

    @GetMapping("/info/{boardId}")
    public ResponseEntity<TravelBoardDetailResponseDTO> getTravelBoardDetail(
            @PathVariable Integer boardId) {
        return ResponseEntity.ok(travelBoardService.getTravelBoardDetail(boardId));
    }
}
