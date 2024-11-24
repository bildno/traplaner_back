package com.traplaner.travelplanservice.travelplan.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.traplaner.common.auth.TokenUserInfo;
import com.project.traplaner.common.dto.CommonResDto;
import com.project.traplaner.travelplan.dto.TravelPlanRequestDTO;
import com.project.traplaner.travelplan.entity.Travel;
import com.project.traplaner.travelplan.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class travelPlanController {
    private final TravelService travelService;

    @GetMapping("/travelplan")
    public String travelPlan() {
        return "travelplan/travelplan";
    }

    @PostMapping("/travelplan")
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

        Travel savedTravel = travelService.saveTravel(requestDTO.getTravel(), userInfo.getEmail());
        travelService.saveJourneys(requestDTO.getJourneys());

        //이거 뭐임...? 기억에 없는데엽...? 내가 쓴건가? 강사님이 고쳐주신건가보당 ㅎㅎ;;; 근데 왜 있어야하는거지ㅠ......?
        //진규님한테 물어봐야겠당.
//        travelService.refreshLoginUserTravel(LoginDto.getEmail(), session);

        CommonResDto resDto =
                new CommonResDto(HttpStatus.CREATED,"여행 여정추가 완료!", savedTravel.getId());


        return new ResponseEntity<>(resDto, HttpStatus.CREATED);
    }
}
