package com.traplaner.mainservice.main.service;

import com.traplaner.mainservice.common.auth.TokenUserInfo;
import com.traplaner.mainservice.main.dto.MainTravelDto;
import com.traplaner.mainservice.main.dto.TopThreeFavoriteTravelDto;
import com.traplaner.mainservice.main.repository.MainTravelRepository;
import com.traplaner.mainservice.common.auth.TokenUserInfo;
import com.traplaner.mainservice.main.repository.MainTravelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {

    private final MainTravelRepository mainTravelRepository;

    public List<TopThreeFavoriteTravelDto> getTop3FavoriteTravels() {

        int pageNumber = 0;
        int desiredFavoriteCount = 3;
        PageRequest pageRequest = PageRequest.of(pageNumber, desiredFavoriteCount);

        // when
        List<TopThreeFavoriteTravelDto> topThreeFavoriteTravel
                = mainTravelRepository.findTopThreeFavoriteTravel(pageRequest);

        return topThreeFavoriteTravel;
    }

    public List<MainTravelDto> getMyTravelList() {

        TokenUserInfo userInfo
                = (TokenUserInfo) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<MainTravelDto> dtoList
                = mainTravelRepository.findAllTravelByMemberEmail(userInfo.getEmail());

        return dtoList;
    }
}
