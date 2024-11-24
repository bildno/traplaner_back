package com.traplaner.travelboardservice.travelBoard.mapper;

import com.traplaner.travelBoard.dto.JourneyResponseDTO;
import com.traplaner.travelBoard.dto.PageDTO;
import com.traplaner.travelBoard.dto.TravelBoardDetailResponseDTO;
import com.traplaner.travelBoard.dto.TravelBoardListResponseDTO;
import com.traplaner.travelBoard.entity.TravelBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TravelBoardMapper {

    // 목록 조회
    List<TravelBoardListResponseDTO> findAll(PageDTO page);

    // 상세 조회
    TravelBoardDetailResponseDTO findOne(int id);

    // 여정 조회
    List<JourneyResponseDTO> journeys(int id);

    // 검색 게시물 개수 카운트
    int getTotalCount(PageDTO page);

    boolean isLikedByMember(Map<String, Integer> travelBoardId);

    void removeLike(Map<String, Integer> travelBoardId);

    void addLike(Map<String, Integer> travelBoardId);

    int getLikeCount(int travelBoardId);

    // 10/28 by jhjeong
    void save(TravelBoard travelBoard);

}
