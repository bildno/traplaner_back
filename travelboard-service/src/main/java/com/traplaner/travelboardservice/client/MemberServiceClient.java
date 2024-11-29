package com.traplaner.travelboardservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "member-service")
public interface MemberServiceClient {
}
