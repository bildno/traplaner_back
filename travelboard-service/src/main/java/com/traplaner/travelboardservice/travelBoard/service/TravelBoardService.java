package com.traplaner.travelboardservice.travelBoard.service;

import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardDetailResponseDTO;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardListResponseDTO;
import com.traplaner.travelboardservice.travelBoard.repository.FavoriteRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class TravelBoardService {
    private final FavoriteRepository favoriteRepository;

    public TravelBoardListResponseDTO getTravelBoardList() {
        return null;
    }

    public TravelBoardDetailResponseDTO getTravelBoardDetail(Integer boardId) {
        return null;
    }
}
