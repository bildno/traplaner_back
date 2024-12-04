package com.traplaner.travelboardservice.client;

import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardInfoDTO;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardListDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.TravelBoardDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.TravelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@FeignClient(name = "mypage-service")
public interface MypageServiceClient {

    @GetMapping("/my-page/mytravelboard")
    Page<TravelBoardListDTO> getBoards(Pageable pageable);

    @GetMapping("/boardInfo/{boardId}")
    CommonResDto<TravelBoardDTO> getBoardInfo(@PathVariable("boardId") Integer boardId);
}
