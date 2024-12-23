package com.traplaner.travelboardservice.client;

import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardListDTO;
import com.traplaner.travelboardservice.travelBoard.dto.request.TravelBoardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "mypage-service")
public interface MypageServiceClient {

    @GetMapping("/getTravelBoard")
    Page<TravelBoardListDTO> getTravelBoards(Pageable pageable);

    @GetMapping("/boardInfo/{boardId}")
    CommonResDto<TravelBoardDTO> getBoardInfo(@PathVariable("boardId") Integer boardId);
}
