package com.traplaner.travelboardservice.client;

import com.traplaner.travelboardservice.travelBoard.dto.response.TravelBoardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mypage-service")
public interface MypageServiceClient {
    @GetMapping("/getTravelBoard/{boardId}")
    Page<TravelBoardDTO> getTravelBoardById(@PathVariable("boardId") Integer id);
}
