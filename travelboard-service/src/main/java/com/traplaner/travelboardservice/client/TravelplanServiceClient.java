package com.traplaner.travelboardservice.client;

import com.traplaner.travelboardservice.travelBoard.dto.JourneyDTO;
import com.traplaner.travelboardservice.travelBoard.dto.TravelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "travelplan-service", url = "http://travelplan-service")
public interface TravelplanServiceClient {
    @GetMapping("/api/travel/{id}")
    TravelDTO getTravelById(@PathVariable("id") Long id);

    @GetMapping("/api/journey/{travelId}")
    List<JourneyDTO> getJourneysByTravelId(@PathVariable("travelId") Long travelId);
}
