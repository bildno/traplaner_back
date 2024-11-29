package com.traplaner.travelplanservice.travelplan.service;

import com.traplaner.travelplanservice.common.util.FileUtils;
import com.traplaner.travelplanservice.travelplan.dto.TravelImgRequestDto;
import com.traplaner.travelplanservice.travelplan.dto.TravelPlanRequestDTO.TravelInfo;
import com.traplaner.travelplanservice.travelplan.dto.TravelResponseDTO;
import com.traplaner.travelplanservice.travelplan.entity.Journey;
import com.traplaner.travelplanservice.travelplan.entity.Travel;
import com.traplaner.travelplanservice.travelplan.repository.JourneyRepository;
import com.traplaner.travelplanservice.travelplan.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static com.traplaner.travelplanservice.travelplan.dto.TravelPlanRequestDTO.JourneyInfo;


@Service
@RequiredArgsConstructor
@Slf4j
public class TravelService {

    private final TravelRepository travelRepository;
    private final JourneyRepository journeyRepository;
    @Value("${file.upload.root-path}")
    private String rootPath;

    public boolean changeShare(int travelId) {
        Travel travel = travelRepository.findById(travelId).orElseThrow(
                () -> {
                    throw new EntityNotFoundException("Travel not found");
                }
        );
        travel.setShare(!travel.isShare());
        travelRepository.save(travel);
        return travel.isShare();
    }

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

    public List<Travel> getTravelsByMemberId(int memberId) {
        return travelRepository.findAllByMemberId(memberId);
    }
    public List<Travel> getTravelsByMemberId(int memberId, Pageable pageable) {
        List<Travel> allByMemberId = travelRepository.findAllByMemberId(memberId, pageable);
        log.info("allByMemberId: {}", allByMemberId);
        return allByMemberId;

    }

    public void putTravelImage(Map<String, String> map) {
        Map<String,String > TravelMap = map;

        for (Map.Entry<String, String> element : TravelMap.entrySet()) {
            int key = Integer.parseInt(element.getKey());
            String value = element.getValue();
            Travel travel = travelRepository.findById(key).orElseThrow(() -> new EntityNotFoundException("Travel not found"));
            travel.setTravelImg(value);
            travelRepository.save(travel);
        }

    }
    public void putJouneyImages(Map<String, String> map) {
        Map<String,String > JourneyMap = map;

        for (Map.Entry<String, String> element : JourneyMap.entrySet()) {
            int key = Integer.parseInt(element.getKey());
            String value = element.getValue();
            Journey journey = journeyRepository.findById(key).orElseThrow(() -> new EntityNotFoundException("Journey not found"));
            journey.setJourneyImg(value);
            //이거 save 위치 가 어디가 좋을려나.
            journeyRepository.save(journey);
        }
    }


}
