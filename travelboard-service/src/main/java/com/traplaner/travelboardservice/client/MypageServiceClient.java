package com.traplaner.travelboardservice.client;

import com.traplaner.travelboardservice.travelBoard.dto.response.TravelBoardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mypage-service")
public interface MypageServiceClient {

    @GetMapping("/my-page/mytravelboard")
    ResponseEntity<?> myBoard(Pageable pageable);

    @GetMapping("/boardInfo/{boardId}")
    ResponseEntity<?> getBoardInfo(@PathVariable("boardId") Integer id);
}
