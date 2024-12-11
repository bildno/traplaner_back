package com.traplaner.mainservice.client;

import com.traplaner.mainservice.common.dto.CommonResDto;
import com.traplaner.mainservice.main.dto.MainTravelDto;
import com.traplaner.mainservice.main.dto.TravelResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "travelplan-service")
public interface TravelplanServiceClient {

    @PostMapping("/top3-travel")
    CommonResDto<List<TravelResponseDTO>> getTop3Travel(@RequestBody List<Integer> travelIds);

    @GetMapping("/travelsByMemberId/{memberId}")
    CommonResDto<List<MainTravelDto>> findById(@PathVariable Integer memberId);

}
