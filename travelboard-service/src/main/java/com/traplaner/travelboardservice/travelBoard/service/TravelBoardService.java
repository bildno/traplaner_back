package com.traplaner.travelboardservice.travelBoard.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

/*    public Page<TravelBoardListDTO> getListDto(Pageable pageable) {
        // Feign Client로 다른 서버에서 TravelBoard 데이터를 가져옴
        Page<TravelBoardListDTO> response = mypageServiceClient.getBoards(pageable);

        // 응답에서 Page<TravelBoardDTO> 추출
        Page<TravelBoardDTO> boards = responsee.getResult();

        // TravelBoardDTO -> TravelBoardListDTO로 변환
        return boards.map(board -> {
            // Travel 데이터 가져오기
            CommonResDto<TravelDTO> travelResDto = travelplanServiceClient.getTravelById(board.getTravelId());
            TravelDTO travel = travelResDto.getResult();

            // Member 데이터 가져오기
            CommonResDto<MemberDTO> memberResDto = memberServiceClient.findById(Integer.valueOf(travel.getMemberId()));
            MemberDTO member = memberResDto.getResult();

            // TravelBoardListDTO 생성 및 반환
            return new TravelBoardListDTO(
                    board.getTravelId(),
                    travel.getTravelImg(),
                    travel.getTitle(),
                    member.getNickName(),
                    board.getWriteDate(),
                    (long) favoriteRepository.getLikeCount(board.getId())
            );
        });
    }*/



    // 특정 게시글 상세 조회
    public TravelBoardInfoDTO getTravelBoardInfo(Integer boardId) {
        //travelboard 가져오기
        CommonResDto<TravelBoardDTO> resDto = mypageServiceClient.getBoardInfo(boardId);
        TravelBoardDTO board = resDto.getResult();
        log.info("board: {}", board);

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
                boardId,
                travel.getTitle(),
                member.getNickName(),
                board.getWriteDate(),
                travel.getTravelImg(),
                board.getContent(),
                (long) favoriteRepository.getLikeCount(board.getId()),
                journeyDetails
        );

        log.info("\n\n\n{}\n\n\n", infoDTO);

        return infoDTO;
    }
}
