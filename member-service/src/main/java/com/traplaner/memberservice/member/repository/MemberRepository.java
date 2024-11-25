package com.traplaner.memberservice.member.repository;

import com.traplaner.memberservice.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {


    Optional<Member> findByEmail(String email);

//    @Query("SELECT m FROM Member m JOIN FETCH m.travels")
//    List<Travel> findAllByEmail();
}

/*
select t.*
from tbl_travel t
JOIN tbl_member tm ON t.member_id = tm.id
where tm.email="test78@abc.net";
 */