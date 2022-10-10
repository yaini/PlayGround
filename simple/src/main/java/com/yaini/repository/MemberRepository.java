package com.yaini.repository;

import com.yaini.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository
    extends JpaRepository<MemberEntity, Long>, MemberQueryDslRepository {}
