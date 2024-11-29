package com.traplaner.travelboardservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "travelplan-service")
public interface TravelplanServiceClient {
}
