package com.traplaner.memberservice.member.service.port;

import com.traplaner.memberservice.member.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository {
    Optional<Member> findByEmail(String email);

    Member save(Member entity);

    Optional<Member> findById(int id);

    boolean duplicateTest(String type, String keyword);
}
