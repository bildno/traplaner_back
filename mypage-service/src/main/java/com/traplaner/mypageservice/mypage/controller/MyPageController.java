package com.traplaner.mypageservice.mypage.controller;

import com.traplaner.mypageservice.mypage.client.MemberServiceClient;
import com.traplaner.mypageservice.mypage.common.auth.TokenUserInfo;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final MemberServiceClient memberServiceClient;



    // 마이페이지 메인 (달력 있는 곳)
    // 달력에 일정 띄워주는 작업 해야댐
    // 계정관리 페이지 작성 필요
    @GetMapping("/my-page")
    @ResponseBody
    public ResponseEntity<?> myPage() {

        List<TravelListResponseDTO> travelListResponseDTOS = myPageService.myPage();
        myPageService.myPage();


        return new ResponseEntity<>(travelListResponseDTOS, HttpStatus.OK);
    }



    // 마이페이지 내 게시물
    @GetMapping("/my-page/mytravelboard")
    @ResponseBody
    public ResponseEntity<?> myBoard(Pageable pageable) {

        Page<TravelBoardResponseDTO> map = myPageService.findBoardAll(pageable);
        List<TravelBoardResponseDTO> content = map.getContent();

        return new ResponseEntity<>(content, HttpStatus.OK);
    }


    // 마이페이지 나의 여행
    @GetMapping("/my-page/my-travel")
    public ResponseEntity<?> myTravel(Pageable pageable) {
        Page<travelPlanResDto> travels = myPageService.myTravel(pageable);
        List<travelPlanResDto> content = travels.getContent();

        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    // 공유여부 변경
    @PostMapping("/my-page/shareIs/{boardId}")
    @ResponseBody
    public ResponseEntity<?> shareIs(@PathVariable int boardId) {
        myPageService.updateShare(boardId);

        return ResponseEntity.ok().body("success");
    }

//     글 삭제
    @PostMapping("/my-page/delete/{boardId}")
    @ResponseBody
    public ResponseEntity<?> deleteBoard(@PathVariable int boardId,
                                         HttpSession session) {



        myPageService.deleteBoard(boardId, session);

        return ResponseEntity.ok().body("success");
    }


    @GetMapping("/my-page/favorite")
    @ResponseBody
    public ResponseEntity<?> favorite(Pageable pageable) {
        HashMap<String, Object> favorite = myPageService.favorite(pageable);

        return new ResponseEntity<>(favorite, HttpStatus.OK);
    }


    // 비밀번호 변경
    @GetMapping("/my-page/pwChange")
    public String pwChange() {


        return "member/my-pw-change";
    }

    @PostMapping("/my-page/changeConfirm")
    @ResponseBody
    public ResponseEntity<?> changeConfirm(@Validated @RequestBody ModifyMemberInfoDTO dto) {

        boolean b = memberServiceClient.updateInfo(dto);


        if (b) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.ok().body("fail");
        }


    }

    // 닉네임 중복체크
    @PostMapping("/my-page/nickNameChk/{newNick}")
    @ResponseBody
    public ResponseEntity<?> nickNameChk(@PathVariable String newNick) {
        String type = "nickname";

        boolean b = memberServiceClient.duplicateTest(type, newNick);
        System.out.println(b);
        if (!b) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.badRequest().body("fail");
        }

    }


    @GetMapping("my-page/board-info/{travelNo}")
    @ResponseBody
    public ResponseEntity<?> boardInfo(@PathVariable int travelNo) {
        log.info("여행번호 {}", travelNo);
        List<TravelJourneyRes> stringObjectMap = myPageService.boardInfo(travelNo);

        return new ResponseEntity<>(stringObjectMap, HttpStatus.OK);
    }


    @Value("${file.upload.root-path}")
    private String rootPath;

    // 게시글 작성
   @PostMapping("/my-page/insert-board")
   @ResponseBody
   public ResponseEntity<?> insertBoard(TravelBoardCreateDto dto){

       TokenUserInfo userinfo = (TokenUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       String email = userinfo.getEmail();
       CommonResDto<MemberResDto> byEmail = memberServiceClient.findByEmail(email);
       MemberResDto result = byEmail.getResult();
       String nickName = result.getNickName();


       if (StringUtils.hasText(dto.getTravelImg().getOriginalFilename())) {

            String savePath = FileUtils.uploadFile(dto.getTravelImg(), rootPath);

            myPageService.updateTravelImg(dto.getTravelId(), savePath);

        }

       int byTravelId = myPageService.findByTravelId(dto.getTravelId());

       if(byTravelId == 0) {
           TravelBoard board = myPageService.createBoard(Math.toIntExact(dto.getTravelId()), nickName, LocalDateTime.now(), dto.getContent());
       }

       if (!dto.getJourneyImage().isEmpty()) {
            for (int i = 0, j = dto.getJourneyId().size(); i < j; i++ ) {
                String save = FileUtils.uploadFile(dto.getJourneyImage().get(i), rootPath);
                if (save != null )myPageService.updateJourneyImg(Long.valueOf(dto.getJourneyId().get(i)), save);
            }
        }

     return new ResponseEntity<>("등록성공",HttpStatus.OK);

   }

}



