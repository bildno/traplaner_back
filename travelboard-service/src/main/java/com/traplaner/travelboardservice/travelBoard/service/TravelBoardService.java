package com.traplaner.travelboardservice.travelBoard.service;

import com.traplaner.travelboardservice.client.MemberServiceClient;
import com.traplaner.travelboardservice.client.MypageServiceClient;
import com.traplaner.travelboardservice.client.TravelplanServiceClient;
import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.*;
import com.traplaner.travelboardservice.travelBoard.dto.response.JourneyDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.MemberDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.TravelBoardDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.TravelDTO;
import com.traplaner.travelboardservice.travelBoard.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
@Transactional
public class TravelBoardService {
    private final TravelplanServiceClient travelplanServiceClient;
    private final MemberServiceClient memberServiceClient;
    private final MypageServiceClient mypageServiceClient;
    private final FavoriteRepository favoriteRepository;

    // 게시판 목록 조회
    public Page<TravelBoardListDTO> getTravelBoardList(Pageable pageable) {
    }


     // 특정 게시글 상세 조회
    public TravelBoardInfoDTO getTravelBoardInfo(Integer boardId) {

        //travelBoard 가져오기
        HashMap<String, Object> boardResDto = mypageServiceClient.getInfo(boardId);
        TravelBoardDTO board = boardResDto

        //travel 가져오기
        CommonResDto<TravelDTO> travelResDto = travelplanServiceClient.getTravelById(/*board.getTravelId()*/88);
        log.info("travelResDto: {}", travelResDto);
        TravelDTO travel = travelResDto.getResult();
        log.info("travel:{}", travel);

        //member 가져오기
        CommonResDto<MemberDTO> memberResDto = memberServiceClient.findById(Integer.valueOf(travel.getMemberId()));
        MemberDTO member =  memberResDto.getResult();
        log.info("member:{}", member);

        //journey 가져오기
        CommonResDto<List<JourneyDTO>> journeysResDto = travelplanServiceClient.getJourneysByTravelId(/*board.getTravelId()*/88);
        List<JourneyDTO> journeys = journeysResDto.getResult();
        log.info("journeys:{}", journeys);

        List<TravelBoardInfoDTO.JourneyInfoDTO> journeyDetails = journeys.stream()
                .map(journey -> new TravelBoardInfoDTO.JourneyInfoDTO(
                        journey.getJourneyName(),
                        journey.getAccommodationName(),
                        journey.getDay(),
                        journey.getStartTime(),
                        journey.getGoogleMapLocationPinInformation(),
                        journey.getJourneyImg()
                ))
                .collect(Collectors.toList());

        // 데이터 조합 후 반환
        return new TravelBoardInfoDTO(
                travel.getId(),
                /*board.getTravelId()*/44,
                travel.getTitle(),
                member.getNickName(),
                /*board.getWriteDate()*/LocalDateTime.now(),
                travel.getTravelImg(),
                /*board.getContent()*/"둠두두둠둠둠 베잉스",
                (long) favoriteRepository.getLikeCount(/*board.getId()*/88),
                journeyDetails
        );
    }
}
