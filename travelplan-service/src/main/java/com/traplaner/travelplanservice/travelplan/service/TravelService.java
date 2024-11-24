package com.traplaner.travelplanservice.travelplan.service;

import com.project.traplaner.common.util.FileUtils;
import com.project.traplaner.main.dto.MainTravelDto;
import com.project.traplaner.member.entity.Member;
import com.project.traplaner.member.mapper.MemberMapper;
import com.project.traplaner.member.repository.MemberRepository;
import com.project.traplaner.travelplan.entity.Travel;
import com.project.traplaner.travelplan.mapper.TravelMapper;
import com.project.traplaner.travelplan.repository.JourneyRepository;
import com.project.traplaner.travelplan.repository.TravelRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

import static com.project.traplaner.travelplan.dto.TravelPlanRequestDTO.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TravelService {

    private final MemberMapper memberMapper;
    private final TravelMapper travelMapper;
    private final MemberRepository memberRepository;
    private final TravelRepository travelRepository;
    private final JourneyRepository journeyRepository;
    @Value("${file.upload.root-path}")
    private String rootPath;

    public Travel saveTravel(TravelInfo travelInfo, String email) {
//        travelMapper.saveTravel(travel.toEntity(memberId));
        //jpa로 변환
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("member not found")
        );

        OffsetDateTime startOffsetDateTime = OffsetDateTime.parse(travelInfo.getStartDate());
        OffsetDateTime endOffsetDateTime = OffsetDateTime.parse(travelInfo.getEndDate());
        Travel travel = Travel.builder()
                .member(member)
                .title(travelInfo.getTitle())
                .startDate(startOffsetDateTime.toLocalDateTime())
                .endDate(endOffsetDateTime.toLocalDateTime())
                        .build();
        return travelRepository.save(travel);

    }

    public void saveJourneys(List<JourneyInfo> journeys) {
        int travelId = travelMapper.getNextTravelId();
        log.info("travelId: {}", travelId);
        Travel travel = travelRepository.findById((long) travelId).orElseThrow(
                ()->new EntityNotFoundException("여정을 넣을 여행이 존재하지 않아용")
        );
        for (JourneyInfo journey : journeys) {
            if(journey.getReservationConfirmImagePath()!=null) {
                String savePath = FileUtils.uploadFile(
                        journey.getReservationConfirmImagePath(), rootPath);
                journeyRepository.save(journey.toEntity(travel,savePath));
            }
            else{
                journeyRepository.save(journey.toEntity(travel));
            }
        }

    }

    public void refreshLoginUserTravel(String email, HttpSession session) {
        List<MainTravelDto> mainTravelDtoList = travelMapper.findByEmail(email);
        session.setAttribute("mainTravelDtoList", mainTravelDtoList);
    }
}
