package com.traplaner.mypageservice.mypage.service;

import com.traplaner.mypageservice.mypage.client.FavoriteServiceClient;
import com.traplaner.mypageservice.mypage.client.MemberServiceClient;
import com.traplaner.mypageservice.mypage.client.TravelPlanServiceClient;
import com.traplaner.mypageservice.mypage.common.auth.TokenUserInfo;
import com.traplaner.mypageservice.mypage.common.dto.CommonResDto;
import com.traplaner.mypageservice.mypage.dto.FavoriteRes;
import com.traplaner.mypageservice.mypage.dto.TravelBoardDto;
import com.traplaner.mypageservice.mypage.dto.TravelJourneyRes;
import com.traplaner.mypageservice.mypage.dto.response.MemberResDto;
import com.traplaner.mypageservice.mypage.dto.response.TravelBoardResponseDTO;
import com.traplaner.mypageservice.mypage.dto.response.TravelListResponseDTO;
import com.traplaner.mypageservice.mypage.dto.response.travelPlanResDto;
import com.traplaner.mypageservice.mypage.entity.TravelBoard;
import com.traplaner.mypageservice.mypage.repository.MyPageTravelBoardRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class MyPageService {

    private final MyPageTravelBoardRepository myPageTravelBoardRepository;
    private final MemberServiceClient memberServiceClient;
    private final TravelPlanServiceClient travelServiceClient;
    private final FavoriteServiceClient favoriteClient;


    // jpa
    public List<TravelListResponseDTO> myPage() {

        TokenUserInfo userinfo = (TokenUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = Integer.parseInt(userinfo.getId());

        CommonResDto<List<travelPlanResDto>> byMemberId = travelServiceClient.findByMemberId(id);

        List<TravelListResponseDTO> dtoList = byMemberId.getResult().stream().map(travel -> new TravelListResponseDTO(travel)).collect(Collectors.toList());

        return dtoList;

    }

    public Page<TravelBoardResponseDTO> findBoardAll(Pageable pageable) {

        TokenUserInfo userinfo = (TokenUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int id = Integer.parseInt(userinfo.getId());
        CommonResDto<MemberResDto> byEmail = memberServiceClient.findById(id);
        MemberResDto memberResDto = byEmail.getResult();
        String nickName = memberResDto.getNickName();

        Page<TravelBoard> byMemberNickName = myPageTravelBoardRepository.findByMemberNickName(nickName, pageable);
        Page<TravelBoardResponseDTO> collect = byMemberNickName.map(travelBoard -> travelBoard.fromEntity());

        return collect;
    }

    public CommonResDto<Page<travelPlanResDto>> myTravel(Pageable pageable) {
        TokenUserInfo user = (TokenUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return travelServiceClient.findByMemberId(Integer.valueOf(user.getId()), pageable.getPageNumber(), pageable.getPageSize());
    }


    public void updateShare(int id) {

        travelServiceClient.updateShareById(id);
    }

    public void deleteBoard(int boardId) {

        myPageTravelBoardRepository.deleteById(boardId);
    }

    public HashMap<String, Object> favorite(Pageable pageable) {
        HashMap<String, Object> map = new HashMap<>();

        TokenUserInfo userinfo = (TokenUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<FavoriteRes> byMemberId = favoriteClient.findByMemberId(Integer.parseInt(userinfo.getId()), pageable.getPageNumber(), pageable.getPageSize());


        List<TravelBoard> travelBoards = byMemberId.stream()
                .map(favoriteRes -> myPageTravelBoardRepository.findById(favoriteRes.getId()).get())
                .collect(Collectors.toList());

        List<travelPlanResDto> travels =
                travelBoards.stream().map(travel ->
                        travelServiceClient.findById(travel.getTravelId())).collect(Collectors.toList());


        map.put("favorites", byMemberId);
        map.put("travelBoards", travelBoards);
        map.put("travels", travels);


        return map;
    }


    public void updateTravelImg(HashMap<String, String> map) {
        travelServiceClient.updateTravelImagesById(map);
    }


    public void updateJourneyImg(HashMap<String, String> map) {
        travelServiceClient.updateJourneyImagesById(map);
    }

    public TravelBoard createBoard(Integer travelId, String memberNickName, LocalDateTime writeDate, String content) {
        TravelBoardDto build = TravelBoardDto.builder()
                .travelId(travelId)
                .memberNickName(memberNickName)
                .writeDate(writeDate)
                .content(content)
                .build();

        TravelBoard travelBoard = build.toEntity();

        return myPageTravelBoardRepository.save(travelBoard);
    }


    public int findByTravelId(int travelId) {
        Long travel = myPageTravelBoardRepository.countById(travelId);

        return Math.toIntExact(travel);
    }


    public HashMap<String, Object> boardInfo(int travelNo) {
        HashMap<String, Object> map = new HashMap<>();

        CommonResDto<List<TravelJourneyRes>> travelById = travelServiceClient.findTravelById(travelNo);
        List<TravelJourneyRes> result = travelById.getResult();

        TravelBoard travel = myPageTravelBoardRepository.findByTravelId(travelNo).orElseThrow(() -> new EntityNotFoundException("없는 글"));
        TravelBoardResponseDTO travelBoardResponseDTO = travel.fromEntity();


        map.put("travelJourneyResDtos", result);
        map.put("travelBoardResponseDTO", travelBoardResponseDTO);
        return map;

    }

    public Page<TravelBoard> getBoardAll(Pageable pageable) {
        Page<TravelBoard> all = myPageTravelBoardRepository.findAll(pageable);

        return all;
    }

    public List<TravelBoardResponseDTO> getBoardIn(List<Integer> boardIds) {

        List<TravelBoard> byIdIn = myPageTravelBoardRepository.findByIdIn(boardIds);
        List<TravelBoardResponseDTO> collect = byIdIn.stream().map(travelBoard -> travelBoard.fromEntity()).collect(Collectors.toList());
        return collect;
    }
}

