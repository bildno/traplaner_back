package com.traplaner.travelboardservice.travelBoard.service;

import com.traplaner.travelBoard.dto.JourneyResponseDTO;
import com.traplaner.travelBoard.mapper.TravelBoardMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class JourneyService {

    private final TravelBoardMapper travelBoardMapper;

    public List<JourneyResponseDTO> getJourney(int id) {
        return travelBoardMapper.journeys(id);
    }

}
