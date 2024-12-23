package com.traplaner.travelboardservice.travelBoard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.traplaner.travelboardservice.client.MemberServiceClient;
import com.traplaner.travelboardservice.client.MypageServiceClient;
import com.traplaner.travelboardservice.client.TravelplanServiceClient;
import com.traplaner.travelboardservice.common.dto.CommonResDto;
import com.traplaner.travelboardservice.travelBoard.dto.*;
import com.traplaner.travelboardservice.travelBoard.dto.request.JourneyDTO;
import com.traplaner.travelboardservice.travelBoard.dto.request.MemberDTO;
import com.traplaner.travelboardservice.travelBoard.dto.request.TravelBoardDTO;
import com.traplaner.travelboardservice.travelBoard.dto.request.TravelDTO;
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
public class TravelBoardService {
    private final TravelplanServiceClient travelplanServiceClient;
    private final MemberServiceClient memberServiceClient;
    private final MypageServiceClient mypageServiceClient;
    private final FavoriteRepository favoriteRepository;

    // travelboard 매핑
    public TravelBoardDTO boardResDto (Integer boardId) {
        CommonResDto<TravelBoardDTO> boardData = mypageServiceClient.getBoardInfo(boardId);
        log.info("boardData: {}", boardData);

        return boardData.getResult();
    }

    private MemberDTO getMemberByMemberId(int memberId) {
        //member 가져오기
        CommonResDto<MemberDTO> memberResDto = memberServiceClient.findById(memberId);
        MemberDTO member = memberResDto.getResult();
        log.info("member:{}", member);
        return member;
    }

    private TravelDTO getTravelByBoardId(int boardId) {
        //travel 가져오기
        CommonResDto<TravelDTO> travelResDto = travelplanServiceClient.getTravelById(boardId);
        log.info("travelResDto: {}", travelResDto);
        TravelDTO travel = travelResDto.getResult();
        log.info("travel:{}", travel);
        return travel;
    }

    public Page<TravelBoardListDTO> getTravelBoardList(Pageable pageable) {
        Page<TravelBoardListDTO> result = mypageServiceClient.getTravelBoards(pageable);

        for(TravelBoardListDTO travelBoardListDTO : result.getContent()) {
            TravelDTO travel = getTravelByBoardId(travelBoardListDTO.getId());
            MemberDTO member = getMemberByMemberId(Integer.parseInt(travel.getMemberId()));

            travelBoardListDTO.setTravelId(travel.getId());
            travelBoardListDTO.setTravelImg(travel.getTravelImg());
            travelBoardListDTO.setTitle(travel.getTitle());
            travelBoardListDTO.setNickName(member.getNickName());
            travelBoardListDTO.setLikeCount(favoriteRepository.getLikeCount(travelBoardListDTO.getId()));
        }

        log.info("result: {}", result);

        return result;
    }




    // 특정 게시글 상세 조회
    public TravelBoardInfoDTO getTravelBoardInfo(Integer boardId) {

        //travelboard 가져오기
        TravelBoardDTO board = boardResDto(boardId);
        log.info("\n\n\n{}\n\n\n", board);

        //travel 가져오기
        CommonResDto<TravelDTO> travelResDto = travelplanServiceClient.getTravelById(board.getTravelId());
        log.info("travelResDto: {}", travelResDto);
        TravelDTO travel = travelResDto.getResult();
        log.info("travel:{}", travel);

        //member 가져오기
        CommonResDto<MemberDTO> memberResDto = memberServiceClient.findById(Integer.valueOf(travel.getMemberId()));
        MemberDTO member = memberResDto.getResult();
        log.info("member:{}", member);

        //journey 가져오기
        CommonResDto<List<JourneyDTO>> journeysResDto = travelplanServiceClient.getJourneysByTravelId(board.getTravelId());
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
                board.getTravelId(),
                board.getId(),
                travel.getTitle(),
                member.getNickName(),
                board.getWriteDate(),
                travel.getTravelImg(),
                board.getContent(),
                favoriteRepository.getLikeCount(board.getId()),
                journeyDetails
        );

        log.info("\n\n\n{}\n\n\n", infoDTO);

        return infoDTO;
    }
}
