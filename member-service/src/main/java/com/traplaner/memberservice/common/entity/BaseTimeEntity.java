package com.traplaner.memberservice.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 테이블과 관련이 없고, 컬럼 정보만 자식에게 제공하기 위해 사용하는 아노테이션.
public abstract class BaseTimeEntity {

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

}
