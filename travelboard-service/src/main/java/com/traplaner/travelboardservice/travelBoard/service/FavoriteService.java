package com.traplaner.travelboardservice.travelBoard.service;

import com.traplaner.travelboardservice.travelBoard.dto.FavoriteDTO;
import com.traplaner.travelboardservice.travelBoard.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
@Transactional
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public List<FavoriteDTO> getTopThree() {
        List<Map<String, Object>> result = favoriteRepository.findTopThreeTravelBoards();

        // 결과를 DTO로 변환
        return result.stream()
                .map(row -> FavoriteDTO.builder()
                        .travelBoardId((Integer) row.get("travelBoardId"))
                        .likeCount((Long) row.get("likeCount"))
                        .build())
                .collect(Collectors.toList());
    }
}
