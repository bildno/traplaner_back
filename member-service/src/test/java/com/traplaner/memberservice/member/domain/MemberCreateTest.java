package com.traplaner.memberservice.member.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberCreateTest {

    @Test
    void MemberCreate로_멤버를_생성할_수_있다() {
        //given
        MemberCreate memberCreate = MemberCreate.builder()
                .email("test@test.com")
                .password("test1234")
                .nickName("test1")
                .profileImage("대충 이미지")
                .build();
        //when
        Member member = memberCreate.toModel();

        //then
        assertThat(member.getNickName()).isEqualTo("test1");
        assertThat(member.getEmail()).isEqualTo("test@test.com");
    }
}