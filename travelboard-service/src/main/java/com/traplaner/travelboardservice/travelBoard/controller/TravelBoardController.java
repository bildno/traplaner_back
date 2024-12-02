package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardInfoDTO;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardListDTO;
import com.traplaner.travelboardservice.travelBoard.service.TravelBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travelboard")
@RequiredArgsConstructor
@Slf4j
public class TravelBoardController {
    private final TravelBoardService travelBoardService;

    @GetMapping("/list")
    public ResponseEntity<Page<TravelBoardListDTO>> getTravelBoardList(
            @PageableDefault(size = 6, sort = "writeDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(travelBoardService.getTravelBoardList(pageable));
    }

    @GetMapping("/info/{boardId}")
    public ResponseEntity<TravelBoardInfoDTO> getTravelBoardInfo(@PathVariable("boardId") Integer boardId) {
        return ResponseEntity.ok(travelBoardService.getTravelBoardInfo(boardId));
    }
}
