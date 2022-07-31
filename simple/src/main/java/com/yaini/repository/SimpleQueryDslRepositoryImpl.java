package com.yaini.repository;

import com.yaini.entity.QSimpleEntity;
import com.yaini.entity.SimpleEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class SimpleQueryDslRepositoryImpl extends QuerydslRepositorySupport implements SimpleQueryDslRepository {

    private final QSimpleEntity simple = QSimpleEntity.simpleEntity;

    public SimpleQueryDslRepositoryImpl() {
        super(SimpleEntity.class);
    }

    @Override
    public boolean existsByNameCustom(final String name) {
        return from(simple)
                .where(simple.name.eq(name))
                .fetchFirst() != null;
    }

}
