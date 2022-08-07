package com.yaini.repository;

import com.yaini.entity.MemberEntity;
import com.yaini.projection.MemberByCountryProjection;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface MemberQueryDslRepository {

    Map<String, List<MemberEntity>> groupByCountry();

    Collection<MemberByCountryProjection> findAllWithCountry();
}
