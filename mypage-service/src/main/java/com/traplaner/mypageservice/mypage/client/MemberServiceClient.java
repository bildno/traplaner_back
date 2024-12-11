package com.traplaner.mypageservice.mypage.client;

import com.traplaner.mypageservice.mypage.common.dto.CommonResDto;
import com.traplaner.mypageservice.mypage.dto.ModifyMemberInfoDTO;
import com.traplaner.mypageservice.mypage.dto.response.MemberResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    @GetMapping("/getMemberById/{id}")
    CommonResDto<MemberResDto> findById(@PathVariable int id);

    @PutMapping("/changeInfoById")
    CommonResDto<?> updateInfo(@RequestBody HashMap<String, String> map);

    @PostMapping("/duplicateTest")
    boolean duplicateTest(@RequestBody HashMap<String, String> map);
}
