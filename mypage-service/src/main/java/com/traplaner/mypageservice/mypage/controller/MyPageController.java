package com.traplaner.mypageservice.mypage.controller;

import com.traplaner.mypageservice.mypage.client.MemberServiceClient;
import com.traplaner.mypageservice.mypage.client.TravelPlanServiceClient;
import com.traplaner.mypageservice.mypage.common.auth.TokenUserInfo;
import com.traplaner.mypageservice.mypage.common.config.AwsS3Config;
import com.traplaner.mypageservice.mypage.common.dto.CommonResDto;
import com.traplaner.mypageservice.mypage.common.util.FileUtils;
import com.traplaner.mypageservice.mypage.dto.FavoriteRes;
import com.traplaner.mypageservice.mypage.dto.ModifyMemberInfoDTO;
import com.traplaner.mypageservice.mypage.dto.TravelJourneyRes;
import com.traplaner.mypageservice.mypage.dto.response.*;
import com.traplaner.mypageservice.mypage.service.MyPageService;
import com.traplaner.mypageservice.mypage.dto.response.TravelBoardCreateDto;
import com.traplaner.mypageservice.mypage.dto.response.TravelBoardResponseDTO;
import com.traplaner.mypageservice.mypage.dto.response.TravelListResponseDTO;
import com.traplaner.mypageservice.mypage.entity.TravelBoard;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberServiceClient memberServiceClient;
    private final TravelPlanServiceClient travelPlanServiceClient;
    private final AwsS3Config s3Config;


    // 마이페이지 메인 (달력 있는 곳)(작동 됨)
    // 달력에 일정 띄워주는 작업 해야댐
    // 계정관리 페이지 작성 필요
    @GetMapping("/my-page")
    public ResponseEntity<?> myPage() {

        List<TravelListResponseDTO> travelListResponseDTOS = myPageService.myPage();

        return new ResponseEntity<>(travelListResponseDTOS, HttpStatus.OK);
    }


    // 마이페이지 내 게시물(얘됨)
    @GetMapping("/my-page/mytravelboard")

    public ResponseEntity<?> myBoard(Pageable pageable) {

            Page<TravelBoardResponseDTO> map = myPageService.findBoardAll(pageable);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    // 마이페이지 나의 여행(작동 댐)
    @GetMapping("/my-page/my-travel")
    public ResponseEntity<?> myTravel(Pageable pageable) {
        CommonResDto<Page<travelPlanResDto>> travels = myPageService.myTravel(pageable);
        Page<travelPlanResDto> result = travels.getResult();
        List<travelPlanResDto> content = result.getContent();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 공유여부 변경(작동 됨)
    @PostMapping("/my-page/shareIs/{boardId}")
    public ResponseEntity<?> shareIs(@PathVariable Integer boardId) {
        myPageService.updateShare(boardId);

        return ResponseEntity.ok().body("success");
    }

    //글 삭제(작동 됨)
    @PostMapping("/my-page/delete/{TravelId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Integer TravelId
    ) {

        myPageService.deleteBoard(TravelId);

        return ResponseEntity.ok().body("success");
    }


    // 좋아요 리스트(작동 안됨: 아마 페이보릿 클라이언트 측이 완성이 안되서 생긴문제인듯)
    @GetMapping("/my-page/favorite")
    public ResponseEntity<?> favorite(Pageable pageable) {
        log.info("/my-page/favorite: GET!, {}", pageable);

        HashMap<String, Object> favorite = myPageService.favorite(pageable);


        return new ResponseEntity<>(favorite, HttpStatus.OK);
    }


    //멤버 정보 변경(작동 됨)
    @PostMapping("/my-page/changeConfirm")
    public ResponseEntity<?> changeConfirm(@Validated @RequestBody ModifyMemberInfoDTO dto) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(dto.getId()));
        map.put("newPw", dto.getNewPw());
        map.put("newNick", dto.getNewNick());

        boolean b = (boolean) memberServiceClient.updateInfo(map).getResult();


        if (b) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.ok().body("fail");
        }


    }

    // 닉네임 중복체크(작동 됨)
    @PostMapping("/my-page/nickNameChk/{newNick}")
    public ResponseEntity<?> nickNameChk(@PathVariable String newNick) {
        String type = "nickname";

        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("keyword", newNick);
        boolean b = memberServiceClient.duplicateTest(map);
        System.out.println(b);
        if (!b) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.badRequest().body("fail");
        }

    }

    // 게시글(작동 됨)
    @GetMapping("my-page/board-info/{travelNo}")
    public ResponseEntity<?> boardInfo(@PathVariable Integer travelNo) {
        HashMap<String, Object> map = new HashMap<>();

        CommonResDto<travelPlanResDto> byId = travelPlanServiceClient.findById(travelNo);
        travelPlanResDto travel = byId.getResult();

        CommonResDto<List<TravelJourneyRes>> dto = travelPlanServiceClient.findTravelById(travelNo);
        TravelBoardResponseDTO travelBoardResponseDTO = myPageService.boardInfoByTravelId(travelNo);
        List<TravelJourneyRes> Journeys = dto.getResult();

        map.put("travel",travel);
        map.put("Journey", Journeys);
        map.put("TravelBoard", travelBoardResponseDTO);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @Value("${file.upload.root-path}")
    private String rootPath;

    // 게시글 작성(아마 안될듯 mutipart파일은 json으로 통신안댐)
    @PostMapping("/my-page/insert-board")
    public ResponseEntity<?> insertBoard(TravelBoardCreateDto dto) throws IOException {

        TokenUserInfo userinfo = (TokenUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = userinfo.getId();
        CommonResDto<MemberResDto> byEmail = memberServiceClient.findById(Integer.parseInt(id));
        MemberResDto result = byEmail.getResult();
        String nickName = result.getNickName();

        HashMap<String, String> jourenyMap = new HashMap<>();
        HashMap<String, String> travelMap = new HashMap<>();

        if(dto.getTravelImg() != null){
            if (StringUtils.hasText(dto.getTravelImg().getOriginalFilename())) {

//                String savePath = FileUtils.uploadFile(dto.getTravelImg(), rootPath);
                MultipartFile travelImage = dto.getTravelImg();
                String uniqueFileName
                        = UUID.randomUUID() + "_" +     travelImage.getOriginalFilename();

                String imageUrl
                        = s3Config.uploadToS3Bucket(travelImage.getBytes(), uniqueFileName);
                travelMap.put(String.valueOf(dto.getTravelId()), imageUrl);
                myPageService.updateTravelImg(travelMap);

            }
        }

        int byTravelId = myPageService.findByTravelId(Math.toIntExact(dto.getTravelId()));

        if (byTravelId == 0) {
            TravelBoard board = myPageService.createBoard(Math.toIntExact(dto.getTravelId()), nickName, LocalDateTime.now(), dto.getContent());
        }


            if (!dto.getJourneyImage().isEmpty()) {

                for (int i = 0, j = dto.getJourneyImage().size(); i < j; i++) {
                    if (dto.getJourneyImage().get(i) != null) {

                        MultipartFile journeyImage = dto.getJourneyImage().get(i);

                        String uniqueFileName
                                = UUID.randomUUID() + "_" +     journeyImage.getOriginalFilename();
                        String imageUrl
                                = s3Config.uploadToS3Bucket(journeyImage.getBytes(), uniqueFileName);

                        jourenyMap.put(String.valueOf(dto.getJourneyId().get(i)),
                                imageUrl);
                    }
                }
                log.info("저니 맵 {}", jourenyMap);
                myPageService.updateJourneyImg(jourenyMap);
            }

        return new ResponseEntity<>("등록성공", HttpStatus.OK);

    }

    //뭔가 이상함 많이 이상함
    @PostMapping("/favoriteTop")
    public ResponseEntity<?> favoriteTop(@RequestBody List<Integer> boardIds) {
        List<TravelBoardResponseDTO> boardIn = myPageService.getBoardIn(boardIds);

        CommonResDto resDto =
                new CommonResDto(HttpStatus.OK,"top3 TravelBoard 조회 완료", boardIn);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    //페이저블로 보드 페이지 뽑기(작동됨)
    @GetMapping("/getTravelBoard")
    public ResponseEntity<?> getTravelBoard(Pageable pageable) {
        Page<TravelBoard> boardAll = myPageService.getBoardAll(pageable);

        return new ResponseEntity<>(boardAll, HttpStatus.OK);
    }

    //작동안됨 dto가 못받음
    @GetMapping("/boardInfo/{boardId}")
    public ResponseEntity<?> getBoardInfo(@PathVariable Integer boardId) {
        TravelBoardResponseDTO dto = myPageService.boardInfo(boardId);
        CommonResDto<TravelBoardResponseDTO> boardInfo
                = new CommonResDto<>(HttpStatus.OK,"TravelBoard 조회완료",dto);
        return new ResponseEntity<>(boardInfo, HttpStatus.OK);
    }


    @GetMapping("/my-page/pwChange")
    public ResponseEntity<?> getMyPagePwChange() {
        CommonResDto<MemberResDto> memberInfo = myPageService.getMemberInfo();

        MemberResDto result = memberInfo.getResult();
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}



