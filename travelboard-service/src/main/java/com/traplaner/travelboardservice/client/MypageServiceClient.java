package com.traplaner.travelboardservice.client;

import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "mypage-service", url = "http://mypage-service")
public interface MypageServiceClient {
    @GetMapping("/api/travelBoard/{id}")
    TravelBoardDTO getTravelBoardById(@PathVariable("id") Long id);
}
