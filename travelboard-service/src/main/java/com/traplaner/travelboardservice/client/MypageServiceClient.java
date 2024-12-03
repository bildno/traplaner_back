package com.traplaner.travelboardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mypage-service")
public interface MypageServiceClient {

    @GetMapping("/my-page/mytravelboard")
    ResponseEntity<?> myBoard(Pageable pageable);

    @GetMapping("/boardInfo/{boardId}")
    ResponseEntity<?> getBoardInfo(@PathVariable("boardId") Integer id);
}
