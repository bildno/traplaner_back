package com.traplaner.memberservice.member.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Integer> {


    Optional<MemberEntity> findByEmail(String email);

    @Query("SELECT COUNT(m) > 0 FROM MemberEntity m WHERE " +
            "(:type = 'nickname' AND m.nickName = :keyword) OR " +
            "(:type = 'email' AND m.email = :keyword)")
    boolean duplicateTest(@Param("type") String type, @Param("keyword") String keyword);

}
