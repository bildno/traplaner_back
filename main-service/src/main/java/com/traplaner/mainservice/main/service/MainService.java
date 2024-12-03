package com.traplaner.mainservice.main.service;

import com.traplaner.mainservice.client.MypageServiceClient;
import com.traplaner.mainservice.client.TravelboardServiceClient;
import com.traplaner.mainservice.client.TravelplanServiceClient;
import com.traplaner.mainservice.common.auth.TokenUserInfo;
import com.traplaner.mainservice.common.dto.CommonResDto;
import com.traplaner.mainservice.main.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {

    // Feign Client 주입
    private final TravelboardServiceClient travelboardServiceClient;
    private final TravelplanServiceClient travelplanServiceClient;
    private final MypageServiceClient mypageServiceClient;

//    public List<TopThreeFavoriteTravelDto> getTop3FavoriteTravels() {
    public List<TravelResponseDTO> getTop3FavoriteTravels() {

        // 1. travelboard-service feign 연동 top 3 favorite table 정보(travelBoardId, likeCount) 획득
        CommonResDto<List<FavoriteResDto>> top3Favorite = travelboardServiceClient.getTop3Favorite();
        List<FavoriteResDto> favoriteResDtos = top3Favorite.getResult();
        log.info("====================>>>> favoriteResDtos: {}", favoriteResDtos);

        // 1.1. travelBoardId 리스트를 만든다.(mypage-service에 요청하기 위한 정보)
        List<Integer> travelBoardIds = favoriteResDtos.stream()
                .map(favoriteResDto -> favoriteResDto.getTravelBoardId())
                .collect(Collectors.toList());

        // 2. mypage-service feign 연동 top 3 tavelId 리스트 획득
        CommonResDto<List<TravelBoardResponseDTO>> top3TravelBoard
                = mypageServiceClient.getTop3TravelBoard(travelBoardIds);

        List<TravelBoardResponseDTO> travelBoardResponseDtos = top3TravelBoard.getResult();
        log.info("====================>>> travelBoardResponseDtos: {}", travelBoardResponseDtos);

        // 2.1. travelID 리스트를 만든다.(travel-service에 요청하기 위한 정보)
        List<Integer> travelIds = travelBoardResponseDtos.stream()
                .map(travelBoardResponseDto -> travelBoardResponseDto.getTravelId())
                .collect(Collectors.toList());

        //3. travel-service feign 연동 top 2 Travel(id, 제목, 이미지) 정보 획득
        CommonResDto<List<TravelResponseDTO>> top3Travel
                = travelplanServiceClient.getTop3Travel(travelIds);

        List<TravelResponseDTO> travelResponseDtos = top3Travel.getResult();
        log.info("====================>>> travelResponseDtos: {}", travelResponseDtos);

        return travelResponseDtos;
    }

    public List<MainTravelDto> getMyTravelList() {

        TokenUserInfo userInfo
                = (TokenUserInfo) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String memberId = userInfo.getMemberId();


        // 1. travelplan-service feign 연동, memberId의 전체 여행 리스트 받아오기
        CommonResDto<List<MainTravelDto>> mainTravelList
                = travelplanServiceClient.findById(Integer.parseInt(memberId));

        List<MainTravelDto> mainTravelDtos = mainTravelList.getResult();


        return mainTravelDtos;

    }
}
