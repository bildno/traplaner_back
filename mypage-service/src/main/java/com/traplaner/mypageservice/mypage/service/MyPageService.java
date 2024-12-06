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

import java.util.Optional;
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


    public void updateShare(Integer id) {

        travelServiceClient.updateShareById(id);
    }

    public void deleteBoard(Integer travelId) {

        TravelBoard tb = myPageTravelBoardRepository.findByTravelId(travelId).orElseThrow(() -> new NullPointerException("업따"));

        favoriteClient.deleteByTravelBoardId(tb.getId());
        myPageTravelBoardRepository.deleteById(tb.getId());
        travelServiceClient.deleteJourney(travelId);
        travelServiceClient.deleteTravel(travelId);

    }

    public HashMap<String, Object> favorite(Pageable pageable) {
        HashMap<String, Object> map = new HashMap<>();

        TokenUserInfo userinfo = (TokenUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("userinfo: {}", userinfo);

        CommonResDto<Page<FavoriteRes>> favorites = favoriteClient.findByMemberId(Integer.parseInt(userinfo.getId()), pageable.getPageNumber(), pageable.getPageSize());
        log.info("favorites: {}", favorites);
        Page<FavoriteRes> result = favorites.getResult();

        List<TravelBoard> travelBoards = result.getContent().stream()
                .map(favoriteRes -> myPageTravelBoardRepository.findById(favoriteRes.getId()).get())
                .collect(Collectors.toList());

        log.info("travelBoards: {}", travelBoards);


//        List<CommonResDto<travelPlanResDto>> collect
//                = travelBoards.stream().map(travelBoard -> travelServiceClient.findById(travelBoard.getTravelId())).collect(Collectors.toList());

        List<Integer> collect
                = travelBoards.stream().map(travelBoard -> travelBoard.getTravelId()).collect(Collectors.toList());
        log.info("collect: {}", collect);

        CommonResDto<List<travelPlanResDto>> top3TravelPlan = travelServiceClient.getTop3TravelPlan(collect);
        log.info("top3TravelPlan: {}", top3TravelPlan);
        List<travelPlanResDto> travels = top3TravelPlan.getResult();

        map.put("favorites", result.getContent());
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

    public TravelBoardResponseDTO boardInfoByTravelId(Integer travelNo) {
        TravelBoard travelBoard = myPageTravelBoardRepository.findByTravelId(travelNo).orElseThrow(
                ()->{
                    throw new EntityNotFoundException("그런 여행아이디를 가진 게시판은 없어용!");
                }
        );
        return travelBoard.fromEntity();
    }
    public Integer findByTravelId(Integer travelId) {
        Long travel = myPageTravelBoardRepository.countById(travelId);

        return Math.toIntExact(travel);
    }


    public TravelBoardResponseDTO boardInfo(Integer travelBoardNo) {
        HashMap<String, Object> map = new HashMap<>();

        TravelBoard travel = myPageTravelBoardRepository.findById(travelBoardNo).orElseThrow(() -> new EntityNotFoundException("없는 글"));
        return travel.fromEntity();

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

//    public TravelBoardResponseDTO boardInfoByTravelId(Integer travelNo) {
//        TravelBoard travelBoard = myPageTravelBoardRepository.findByTravelId(travelNo).orElseThrow(
//                ()->{
//                    throw new EntityNotFoundException("그런 여행아이디를 가진 게시판은 없어용!");
//                }
//        );
//        return travelBoard.fromEntity();
//    }
}

