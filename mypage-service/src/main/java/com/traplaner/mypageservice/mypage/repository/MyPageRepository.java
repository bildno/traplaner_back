package com.traplaner.mypageservice.mypage.repository;

import com.traplaner.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<Member, Integer> {

}
