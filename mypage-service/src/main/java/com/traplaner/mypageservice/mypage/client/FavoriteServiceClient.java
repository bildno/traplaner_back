package com.traplaner.mypageservice.mypage.client;

import com.traplaner.mypageservice.mypage.dto.FavoriteRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@FeignClient(name="travelboard-service")
public interface FavoriteServiceClient {

    List<FavoriteRes> findByMemberId(int i, Pageable pageable);
}
