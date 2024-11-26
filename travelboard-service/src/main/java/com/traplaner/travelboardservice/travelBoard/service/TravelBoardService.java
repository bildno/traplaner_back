package com.traplaner.travelboardservice.travelBoard.service;

import com.traplaner.travelboardservice.travelBoard.dto.*;
import com.traplaner.travelboardservice.travelBoard.mapper.TravelBoardMapper;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class TravelBoardService {

    private final TravelBoardMapper travelBoardMapper;

    // mapper로 전달받은 entity list를 dto list로 변환해서 controller에게 리턴
    public Map<String, Object> getList(PageDTO page) {

        List<TravelBoardListResponseDTO> travelBoardListResponseDTOS = travelBoardMapper.findAll(page);
        PageMaker pageMaker = new PageMaker(page, travelBoardMapper.getTotalCount(page));

        List<TravelBoardListResponseDTO> dtoList = new ArrayList<>(travelBoardListResponseDTOS);

        Map<String, Object> result = new HashMap<>();
        result.put("tbList", dtoList);
        result.put("pm", pageMaker);
        return result;
    }

    public Map<String, Object> getOne(int id, HttpSession session) {

        TravelBoardDetailResponseDTO travelBoardDetailResponseDTO = travelBoardMapper.findOne(id);
        log.info("게시글 상세보기: {}", travelBoardDetailResponseDTO);

        List<JourneyResponseDTO> journeyResponseDTOS = travelBoardMapper.journeys(travelBoardDetailResponseDTO.getTravelId());
        List<JourneyResponseDTO> journey = new ArrayList<>(journeyResponseDTOS);

        boolean flag = false;
        if (session.getAttribute("login") != null) {
            LoginUserResponseDTO login = (LoginUserResponseDTO) session.getAttribute("login");
            flag = travelBoardMapper.isLikedByMember(Map.of("travelBoardId", id, "memberId", login.getId()));
        }

        log.info("좋아요 여부: {}", flag);

        Map<String, Object> result = new HashMap<>();
        result.put("tOne", travelBoardDetailResponseDTO);
        result.put("journey", journey);
        result.put("likeFlag", flag);

        return result;
    }

    // 좋아요 상태 토글
    @Transactional
    public int toggleLike(int travelBoardId, int memberId) {
        boolean isLiked = travelBoardMapper.isLikedByMember(Map.of("travelBoardId", travelBoardId, "memberId", memberId));
        if (isLiked) {
            travelBoardMapper.removeLike(Map.of("travelBoardId", travelBoardId, "memberId", memberId));
        } else {
            travelBoardMapper.addLike(Map.of("travelBoardId", travelBoardId, "memberId", memberId));
        }
        return travelBoardMapper.getLikeCount(travelBoardId);  // 현재 좋아요 수 반환
    }

}
