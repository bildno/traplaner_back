package com.traplaner.mainservice.main.controller;

import com.traplaner.mainservice.main.dto.MainTravelDto;
import com.traplaner.mainservice.main.dto.TopThreeFavoriteTravelDto;
import com.traplaner.mainservice.main.service.MainService;
import com.traplaner.mainservice.common.dto.CommonResDto;
import com.traplaner.mainservice.main.dto.TopThreeFavoriteTravelDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("/top3-favorite")
    public ResponseEntity<?> listTop3FavoriteTravel() {
        log.info("===============> listTop3FavoriteTravel");
                List<TopThreeFavoriteTravelDto> top3FavoriteTravels
                        = mainService.getTop3FavoriteTravels();

        CommonResDto resDto
                = new CommonResDto(
                HttpStatus.OK, "Top3 Favorite Travels", top3FavoriteTravels);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

    @GetMapping("/mytravel-list")
    public ResponseEntity<?> myTravelList(){
        log.info("myTravelList");
        List<MainTravelDto> myTravelList = mainService.getMyTravelList();

        CommonResDto resDto
                = new CommonResDto(
                        HttpStatus.OK, "My Travel List", myTravelList);

        return new ResponseEntity<>(resDto, HttpStatus.OK);
    }

}
