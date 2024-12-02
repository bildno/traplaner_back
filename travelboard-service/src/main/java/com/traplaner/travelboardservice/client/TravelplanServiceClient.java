package com.traplaner.travelboardservice.client;

import com.traplaner.travelboardservice.travelBoard.dto.response.JourneyDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.TravelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "travelplan-service")
public interface TravelplanServiceClient {
    @GetMapping("/getTravel/{travelId}")
    TravelDTO getTravelById(@PathVariable("travelId") Integer id);

    @GetMapping("/getJourney/{travelId}")
    List<JourneyDTO> getJourneysByTravelId(@PathVariable("travelId") Integer travelId);
}