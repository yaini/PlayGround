package com.yaini.repository;

import com.yaini.entity.SimpleEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimpleRepository extends CustomJPARepository<SimpleEntity, Long> {

    Optional<SimpleEntity> findByName(final String name);

    void deleteAllByName(String name);
}
