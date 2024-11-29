package com.traplaner.travelboardservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("mypage-service")
public interface MypageServiceClient {
}
