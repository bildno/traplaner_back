package com.traplaner.mypageservice.mypage.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class TravelJourneyRes {

     Long id;
     LocalDateTime startTime;
     LocalDateTime endTime;
     String journeyImg;
     String journeyName;


}
