package com.traplaner.travelplanservice.travelplan.service;

import com.traplaner.travelplanservice.common.util.FileUtils;
import com.traplaner.travelplanservice.travelplan.dto.TravelPlanRequestDTO.TravelInfo;
import com.traplaner.travelplanservice.travelplan.entity.Travel;
import com.traplaner.travelplanservice.travelplan.repository.JourneyRepository;
import com.traplaner.travelplanservice.travelplan.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

import static com.traplaner.travelplanservice.travelplan.dto.TravelPlanRequestDTO.JourneyInfo;


@Service
@RequiredArgsConstructor
@Slf4j
public class TravelService {

    private final TravelRepository travelRepository;
    private final JourneyRepository journeyRepository;
    @Value("${file.upload.root-path}")
    private String rootPath;

    public Travel saveTravel(TravelInfo travelInfo, int memberId) {

        OffsetDateTime startOffsetDateTime = OffsetDateTime.parse(travelInfo.getStartDate());
        OffsetDateTime endOffsetDateTime = OffsetDateTime.parse(travelInfo.getEndDate());
        Travel travel = Travel.builder()
                .memberId(memberId)
                .title(travelInfo.getTitle())
                .startDate(startOffsetDateTime.toLocalDateTime())
                .endDate(endOffsetDateTime.toLocalDateTime())
                        .build();
        return travelRepository.save(travel);

    }

    public void saveJourneys(List<JourneyInfo> journeys) {
        int travelId = travelRepository.findTopByOrderByIdDesc().getId();
        log.info("travelId: {}", travelId);
        for (JourneyInfo journey : journeys) {
            if(journey.getReservationConfirmImagePath()!=null) {
                String savePath = FileUtils.uploadFile(
                        journey.getReservationConfirmImagePath(), rootPath);
                journeyRepository.save(journey.toEntity(travelId,savePath));
            }
            else{
                journeyRepository.save(journey.toEntity(travelId));
            }
        }

    }

    public String getImagePathByTravelId(int travelId) {
       Travel travel = travelRepository.findById(travelId).orElseThrow(
               ()-> new EntityNotFoundException("Travel with id" + travelId + "Not Found")
       );
       return travel.getTravelImg();
    }


//    public void refreshLoginUserTravel(String email, HttpSession session) {
//        List<MainTravelDto> mainTravelDtoList = travelMapper.findByEmail(email);
//        session.setAttribute("mainTravelDtoList", mainTravelDtoList);
//    }
}
