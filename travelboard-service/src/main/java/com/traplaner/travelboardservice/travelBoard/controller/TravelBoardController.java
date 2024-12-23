package com.traplaner.travelboardservice.travelBoard.controller;

import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardInfoDTO;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardListDTO;
import com.traplaner.travelboardservice.travelBoard.service.TravelBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TravelBoardController {
    private final TravelBoardService travelBoardService;

    // 게시글 전체 조회
    @GetMapping("/list")
    public ResponseEntity<Page<TravelBoardListDTO>> getTravelBoardList(@PageableDefault(direction = Sort.Direction.DESC, sort = "writeDate") Pageable pageable) {
        Page<TravelBoardListDTO> list = travelBoardService.getTravelBoardList(pageable);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "보드 리스트 조회 완료!", list);
        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

    // 특정 게시글 상세 조회
    @GetMapping("/info/{boardId}")
    public ResponseEntity<TravelBoardInfoDTO> getTravelBoardInfo(@PathVariable("boardId") Integer boardId) {
        TravelBoardInfoDTO info = travelBoardService.getTravelBoardInfo(boardId);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "보드 상세 조회 완료!", info);
        return new ResponseEntity(commonResDto, HttpStatus.OK);
    }

}
