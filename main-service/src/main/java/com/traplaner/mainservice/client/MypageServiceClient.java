package com.traplaner.mainservice.client;

import com.traplaner.mainservice.common.dto.CommonResDto;
import com.traplaner.mainservice.main.dto.TravelBoardResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "mypage-service")
public interface MypageServiceClient {

//    @PostMapping("/mypage-service/top3-travelBoard")
    @PostMapping("/favoriteTop")
    CommonResDto<List<TravelBoardResponseDTO>> getTop3TravelBoard(@RequestBody List<Integer> travelBoardIds);

}


/*
1.  @GetMapping("travelboard-service/top3-favorite")
    CommonResDto<List<FavoriteResDto>> getTop3Favorite();
2. @PostMapping("/mypage-service/top3-travelBoard")
    CommonResDto<List<TravelBoardResDto>> getTop3TravelBoard(@RequestBody List<Integer> travelBoardIds);
3. @PostMapping("/travelplan-service/top3-travel")
    CommonResDto<List<TravelResDto>> getTop3Travel(@RequestBody List<Integer> travelIds);
4. @GetMapping("travelplan-service/travelsByMemberId/{memberId}")
   CommonResDto<List<TravelResDto>> findById(@PathVariable Integer memberId); (편집됨)
 */