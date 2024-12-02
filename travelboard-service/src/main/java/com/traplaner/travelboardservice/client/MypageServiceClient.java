package com.traplaner.travelboardservice.client;

import com.traplaner.travelboardservice.travelBoard.dto.response.TravelBoardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mypage-service")
public interface MypageServiceClient {

    @GetMapping("/my-page/mytravelboard")
    Page<TravelBoardDTO> getTravelBoards(@RequestParam("page") int page,
                                         @RequestParam("size") int size,
                                         @RequestParam("sort") String sort);

    @GetMapping("/my-page/board-info/{travelNo}")
    TravelBoardDTO getTravelBoardById(@PathVariable("travelNo") Integer id);
}
