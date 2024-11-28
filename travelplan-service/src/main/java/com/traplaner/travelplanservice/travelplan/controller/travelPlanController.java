package com.traplaner.travelplanservice.travelplan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.traplaner.travelplanservice.common.auth.TokenUserInfo;
import com.traplaner.travelplanservice.common.dto.CommonResDto;
import com.traplaner.travelplanservice.travelplan.dto.TravelImgRequestDto;
import com.traplaner.travelplanservice.travelplan.dto.TravelPlanRequestDTO;
import com.traplaner.travelplanservice.travelplan.entity.Journey;
import com.traplaner.travelplanservice.travelplan.entity.Travel;
import com.traplaner.travelplanservice.travelplan.repository.JourneyRepository;
import com.traplaner.travelplanservice.travelplan.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class travelPlanController {
    private final TravelService travelService;
    private final JourneyRepository journeyRepository;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> travelSave(@AuthenticationPrincipal TokenUserInfo userInfo,
                                        @RequestParam("data") String data,
                                        @RequestParam Map<String, MultipartFile> reservationFiles
                             ) throws JsonProcessingException {

        log.info("reservationFile: {}", reservationFiles);

        // json을 dto로 받아주는 역할을 하는 jackson라이브러리의 객체
        ObjectMapper objectMapper = new ObjectMapper();
        TravelPlanRequestDTO requestDTO = objectMapper.readValue(data, TravelPlanRequestDTO.class);


        for (Map.Entry<String, MultipartFile> entry : reservationFiles.entrySet()) {
            // 이미지 파일만 따로 Map으로 받았어요.
            // 키: reservation_인덱스 : 파일(멀티파트)
            String key = entry.getKey();
            MultipartFile file = entry.getValue();

            // reservation_0, reservation_1, reservation_2.....
            int journeyId = Integer.parseInt(key.split("_")[1]); // 0, 1, 2 ...
            requestDTO.getJourneys().get(journeyId).setReservationConfirmImagePath(file);
        }


        //서비스 로직으로 전환된 json 데이터 전달

        Travel savedTravel = travelService.saveTravel(requestDTO.getTravel(), Integer.parseInt(userInfo.getId()));
        travelService.saveJourneys(requestDTO.getJourneys());

        //이거 뭐임...? 기억에 없는데엽...? 내가 쓴건가? 강사님이 고쳐주신건가보당 ㅎㅎ;;; 근데 왜 있어야하는거지ㅠ......?
        //진규님한테 물어봐야겠당.
//        travelService.refreshLoginUserTravel(LoginDto.getEmail(), session);

        CommonResDto resDto =
                new CommonResDto(HttpStatus.CREATED,"여행 여정추가 완료!", savedTravel.getId());


        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }

    //사용자 아이디로 여행 리스트 조회
    @GetMapping("/travelsByMemberId/{memberId}")
    public ResponseEntity<?> getTravelsByMemberId(@PathVariable("memberId") int memberId) {
        log.info("memberId: {}", memberId);
        List<Travel> travels = travelService.getTravelsByMemberId(memberId);
        CommonResDto resDto =
                new CommonResDto(HttpStatus.OK,"모든 여행 리스트 조회 완료",travels);
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }
    //여행 아이디로 여정 조회
    @GetMapping("/journeysByTravelId/{travelId}")
    public ResponseEntity<?> getJourneysByTravelId(@PathVariable("travelId") int travelId) {
        List<Journey> journeys = journeyRepository.findAllByTravelId(travelId);
        CommonResDto resDto =
                new CommonResDto(HttpStatus.OK,"모든 여정 조회 완료",journeys);
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }
    //멤버 아이디와 페이저블로 여행 리스트 주기
    @PostMapping("/travelListsByMemberId")
    public ResponseEntity<?> travelListsByMemberId(@RequestParam("memberId") int memberId) {
        return null;
    }
    @GetMapping("/travelListsByMemberId")
    // 페이징이 필요합니다.(주의할 점! 첫번째 페이지는 0페이지이다!)
    // 컨트롤러 파라미터로 Pageable 선언하면, 페이징 파라미터 처리를 쉽게 진행할 수 있음.
    // /list?page=1&size=10&sort=name,desc 요런 식으로.
    // 요청 시 쿼리스트링이 전달되지 않으면 기본값 0, 20, unsorted
    public ResponseEntity<?> travelListsByMemberId(@RequestParam("memberId") int memberId,
                                                  Pageable pageable) {
        log.info("/travelListsByMember : GET, memberId: {}", memberId);
        log.info("/travelListsByMember: GET, pageable={}", pageable);
        List<Travel> travels = travelService.getTravelsByMemberId(memberId, pageable);
        log.info("travels={}", travels);

        CommonResDto resDto
                = new CommonResDto(HttpStatus.OK, "여행리스트 정상조회 완료", travels);
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }
    //여행 id로 share 여부 바꾸기
    @GetMapping("/changeShare/{travelId}")
    public ResponseEntity<?> changeShareByTravelId(@PathVariable("travelId") int travelId) {
        boolean isShare = travelService.changeShare(travelId);
        CommonResDto resDto =
                new CommonResDto(HttpStatus.OK,"share 변경 성공", isShare);
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }
    //여행 아이디로 여행 여정 이미지 수정
    @PostMapping("/putTravelJourneyImages")
    public ResponseEntity<?> putTravelJourneyImages(@RequestBody TravelImgRequestDto dto) {
        log.info("dto: {}", dto);
        travelService.putTravelJouneyImages(dto);
        CommonResDto resDto =
                new CommonResDto(HttpStatus.OK,"이미지 등록 성공","");
        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }
}
