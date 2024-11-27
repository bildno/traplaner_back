package com.traplaner.mypageservice.mypage.client;

import com.traplaner.mypageservice.mypage.common.dto.CommonResDto;
import com.traplaner.mypageservice.mypage.dto.ModifyMemberInfoDTO;
import com.traplaner.mypageservice.mypage.dto.response.MemberResDto;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Optional;

@FeignClient(name = "member-service")
public interface MemberServiceClient {

    CommonResDto<MemberResDto> findByEmail(String email);

    boolean updateInfo(ModifyMemberInfoDTO dto);

    boolean duplicateTest(String type, String newNick);
}
