package com.traplaner.travelboardservice.travelBoard.service;

import com.traplaner.travelboardservice.client.MemberServiceClient;
import com.traplaner.travelboardservice.client.MypageServiceClient;
import com.traplaner.travelboardservice.client.TravelplanServiceClient;
import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.*;
import com.traplaner.travelboardservice.travelBoard.dto.response.JourneyDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.MemberDTO;
import com.traplaner.travelboardservice.travelBoard.dto.response.TravelDTO;
import com.traplaner.travelboardservice.travelBoard.repository.FavoriteRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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



    // travelboard 매핑
/*    public TravelBoardDTO boardResDto (Integer boardId) {
        Map<String, Object> boardData = mypageServiceClient.getBoardInfo(boardId);

        // ObjectMapper를 사용한 매핑
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(boardData, TravelBoardDTO.class);
    }*/

    // 특정 게시글 상세 조회
    public TravelBoardInfoDTO getTravelBoardInfo(Integer boardId) {
/*        //travelboard 가져오기
        TravelBoardDTO board = boardResDto(boardId);
        log.info("\n\n\n{}\n\n\n", boardId);*/

        //travel 가져오기
        CommonResDto<TravelDTO> travelResDto = travelplanServiceClient.getTravelById(/*board.getTravelId()*/43);
        log.info("travelResDto: {}", travelResDto);
        TravelDTO travel = travelResDto.getResult();
        log.info("travel:{}", travel);

        //member 가져오기
        CommonResDto<MemberDTO> memberResDto = memberServiceClient.findById(Integer.valueOf(travel.getMemberId()));
        MemberDTO member = memberResDto.getResult();
        log.info("member:{}", member);

        //journey 가져오기
        CommonResDto<List<JourneyDTO>> journeysResDto = travelplanServiceClient.getJourneysByTravelId(/*board.getTravelId()*/43);
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
        TravelBoardInfoDTO infoDTO = new TravelBoardInfoDTO(
                /*board.getTravelId()*/43,
                /*board.getId()*/boardId,
                travel.getTitle(),
                member.getNickName(),
                /*board.getWriteDate()*/"2024-12-04",
                travel.getTravelImg(),
                /*board.getContent()*/"룰루랄라",
                (long) favoriteRepository.getLikeCount(/*board.getId()*/boardId),
                journeyDetails
        );

        log.info("\n\n\n{}\n\n\n", infoDTO);

        return infoDTO;
    }
}
