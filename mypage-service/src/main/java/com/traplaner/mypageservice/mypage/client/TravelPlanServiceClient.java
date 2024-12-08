package com.traplaner.mypageservice.mypage.client;

import com.traplaner.mypageservice.mypage.common.dto.CommonResDto;
import com.traplaner.mypageservice.mypage.dto.TravelJourneyRes;
import com.traplaner.mypageservice.mypage.dto.response.travelPlanResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.lang.constant.ConstantDesc;
import java.util.HashMap;
import java.util.List;

@FeignClient(name = "travelplan-service")
public interface TravelPlanServiceClient {


    @GetMapping("/travelsByMemberId/{id}")
    CommonResDto<List<travelPlanResDto>> findByMemberId(@PathVariable Integer id);
//
//    @GetMapping("getTravelById/{travelId}")
//    CommonResDto<travelPlanResDto> findById(@PathVariable int travelId);

    @PostMapping("/top3-travel")
    CommonResDto<List<travelPlanResDto>> getTop3TravelPlan(@RequestBody List<Integer> ids);

    @GetMapping("/travelListsByMemberId?memberId={memberId}&page={page}&size={size}")
    CommonResDto<Page<travelPlanResDto>> findByMemberId(@PathVariable Integer memberId, @PathVariable int page, @PathVariable int size);

    @GetMapping("journeysByTravelId/{travelId}")
    CommonResDto<List<TravelJourneyRes>> findTravelById(@PathVariable Integer travelId);


    @PostMapping("/putTravelImage")
    void updateTravelImagesById(@RequestBody HashMap<String, String> map);


    @PostMapping("/putJourneyImages")
    void updateJourneyImagesById(@RequestBody HashMap<String, String> map);

    @GetMapping("/changeShare/{id}")
    void updateShareById(@PathVariable int id);

    @PostMapping("/deleteTravel")
    void deleteTravel(@RequestBody Integer travelId);

    @PostMapping("/deleteJourney")
    void deleteJourney(@RequestBody Integer travelId);
}
