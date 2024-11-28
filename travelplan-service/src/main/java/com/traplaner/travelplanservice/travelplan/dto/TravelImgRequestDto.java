package com.traplaner.travelplanservice.travelplan.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
@Getter
@Setter
@ToString
public class TravelImgRequestDto {

    private Map<String, String> travelInfo;
    private Map<String, String> journeyInfos;

}
