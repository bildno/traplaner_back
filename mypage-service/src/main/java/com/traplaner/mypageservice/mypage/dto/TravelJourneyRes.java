package com.traplaner.mypageservice.mypage.dto;

import java.time.LocalDateTime;

public interface TravelJourneyRes {

     LocalDateTime getStartDate();
     LocalDateTime getEndDate();
     String getJourneyImg();
     String getJourneyName();


}
