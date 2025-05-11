package com.traplaner.memberservice.member.mock;

import com.traplaner.memberservice.member.domain.Member;
import com.traplaner.memberservice.member.service.port.MemberRepository;

import java.util.Optional;

public class FakeMemberRepository implements MemberRepository {
    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Member save(Member entity) {
        return entity;
    }

    @Override
    public Optional<Member> findById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean duplicateTest(String type, String keyword) {
        return false;
    }
}
