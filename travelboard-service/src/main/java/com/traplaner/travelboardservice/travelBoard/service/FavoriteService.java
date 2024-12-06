package com.traplaner.travelboardservice.travelBoard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traplaner.travelboardservice.client.MypageServiceClient;
import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.response.FavoriteResDTO;
import com.traplaner.travelboardservice.travelBoard.dto.request.TravelBoardDTO;
import com.traplaner.travelboardservice.travelBoard.entity.Favorite;
import com.traplaner.travelboardservice.travelBoard.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<FavoriteResDTO> getTopThree() {
        List<Map<String, Object>> result = favoriteRepository.findTopThreeTravelBoards();

        // 결과를 DTO로 변환
        return result.stream()
                .map(row -> FavoriteResDTO.builder()
                        .travelBoardId((Integer) row.get("travelBoardId"))
                        .likeCount((Long) row.get("likeCount"))
                        .build())
                .collect(Collectors.toList());
    }

    // 좋아요 상태 토글
    @Transactional
    public Long toggleLike(Integer travelBoardId, int memberId) {
        boolean isLiked = favoriteRepository.isLikedByMember(travelBoardId, memberId);
        if (isLiked) {
            favoriteRepository.removeLike(travelBoardId,memberId);
        } else {
            Favorite favorite = Favorite.builder()
                    .memberId(memberId)
                    .travelBoardId(travelBoardId)
                    .build();
            favoriteRepository.save(favorite);
        }
        return favoriteRepository.getLikeCount(travelBoardId);  // 현재 좋아요 수
    }

    // 내가 좋아요한 게시물
    public Page<Favorite> myFavorites(Integer memberId, Pageable pageable) {
        Page<Favorite> favorites = favoriteRepository.findAllByMemberId(memberId, pageable);
        return favorites;
    }
}
