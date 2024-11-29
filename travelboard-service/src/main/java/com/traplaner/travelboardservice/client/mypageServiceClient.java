package com.traplaner.travelboardservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "mypage-service")
public class mypageServiceClient {
}
