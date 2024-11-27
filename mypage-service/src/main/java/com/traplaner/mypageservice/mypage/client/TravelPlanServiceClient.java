package com.traplaner.mypageservice.mypage.client;

import com.traplaner.mypageservice.mypage.common.dto.CommonResDto;
import com.traplaner.mypageservice.mypage.dto.TravelJourneyRes;
import com.traplaner.mypageservice.mypage.dto.response.travelPlanResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@FeignClient(name = "travelplan-service")
public interface TravelPlanServiceClient {


    CommonResDto<List<travelPlanResDto>> findByMemberId(Integer id);


    travelPlanResDto findById(Long travelId);

    Page<travelPlanResDto> findByMemberId(Integer id, Pageable pageable);

    List<TravelJourneyRes> findTravelById(int travelNo);

    void updateTravelImagesById(int travelId, String savePath);

    void updateJourneyImagesById(int journeyId, String save);

    void updateShareById(int id);
}
