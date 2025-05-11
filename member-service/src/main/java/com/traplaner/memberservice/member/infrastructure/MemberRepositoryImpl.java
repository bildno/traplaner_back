package com.traplaner.memberservice.member.infrastructure;

import com.traplaner.memberservice.member.domain.Member;
import com.traplaner.memberservice.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email).map(MemberEntity::toModel);
    }

    @Override
    public Member save(Member member) {
        //도메인은 인프라 레이어의 정보를 모르는것이 좋다.
        return memberJpaRepository.save(MemberEntity.fromModel(member)).toModel();
    }

    @Override
    public Optional<Member> findById(int id) {
        return memberJpaRepository.findById(id).map(MemberEntity::toModel);
    }

    @Override
    public boolean duplicateTest(String type, String keyword) {
        return memberJpaRepository.duplicateTest(type, keyword);
    }
}
