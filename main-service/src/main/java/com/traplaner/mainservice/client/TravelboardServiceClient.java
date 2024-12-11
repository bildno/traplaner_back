package com.traplaner.mainservice.client;

import com.traplaner.mainservice.common.dto.CommonResDto;
import com.traplaner.mainservice.main.dto.FavoriteResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "travelboard-service")
public interface TravelboardServiceClient {

    @GetMapping("/top3-favorite")
    CommonResDto<List<FavoriteResDto>> getTop3Favorite();

}
