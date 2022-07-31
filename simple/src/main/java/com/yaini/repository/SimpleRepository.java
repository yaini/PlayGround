package com.yaini.repository;

import com.yaini.entity.SimpleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRepository extends JpaRepository<SimpleEntity, Long>, SimpleQueryDslRepository {

    boolean existsByNameCustom(String name);

    void deleteAllByName(String name);
}
