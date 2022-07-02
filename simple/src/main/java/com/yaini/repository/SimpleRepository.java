package com.yaini.repository;

import com.yaini.entity.SimpleEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRepository extends CustomJPARepository<SimpleEntity, Long> {

    void deleteAllByName(String name);
}
