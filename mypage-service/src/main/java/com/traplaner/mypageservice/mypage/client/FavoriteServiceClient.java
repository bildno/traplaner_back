package com.traplaner.mypageservice.mypage.client;

import com.traplaner.mypageservice.mypage.common.dto.CommonResDto;
import com.traplaner.mypageservice.mypage.dto.FavoriteRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="travelboard-service")
public interface FavoriteServiceClient {

    @GetMapping("/my-favoriteList/{id}?page={page}&size={size}" )
    CommonResDto<Page<FavoriteRes>> findByMemberId(@PathVariable int id, @PathVariable int page, @PathVariable int size);
}
