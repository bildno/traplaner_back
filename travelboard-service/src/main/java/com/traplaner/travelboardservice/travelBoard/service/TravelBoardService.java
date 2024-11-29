package com.traplaner.travelboardservice.travelBoard.service;

import com.traplaner.travelboardservice.client.MemberServiceClient;
import com.traplaner.travelboardservice.client.MypageServiceClient;
import com.traplaner.travelboardservice.client.TravelplanServiceClient;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardDetailResponseDTO;
import com.traplaner.travelboardservice.travelBoard.dto.TravelBoardListResponseDTO;
import com.traplaner.travelboardservice.travelBoard.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
@Transactional
public class TravelBoardService {
    private final FavoriteRepository favoriteRepository;

    private final MemberServiceClient memberServiceClient;
    private final TravelplanServiceClient travelplanServiceClient;
    private final MypageServiceClient mypageServiceClient;

    public TravelBoardListResponseDTO getTravelBoardList() {
        return null;
    }

    public TravelBoardDetailResponseDTO getTravelBoardDetail(Integer boardId) {
        return null;
    }
}
