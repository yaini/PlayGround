package com.yaini.repository;

import static com.querydsl.core.group.GroupBy.list;

import com.querydsl.core.group.GroupBy;
import com.yaini.entity.MemberEntity;
import com.yaini.entity.QMemberEntity;
import com.yaini.entity.QTeamEntity;
import com.yaini.projection.MemberByCountryProjection;
import com.yaini.projection.QMemberByCountryProjection;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberQueryDslRepositoryImpl extends QuerydslRepositorySupport
    implements MemberQueryDslRepository {

  private final QTeamEntity team = QTeamEntity.teamEntity;
  private final QMemberEntity member = QMemberEntity.memberEntity;

  public MemberQueryDslRepositoryImpl() {
    super(MemberEntity.class);
  }

  @Override
  public Map<String, List<MemberEntity>> groupByCountry() {
    return from(member)
        .leftJoin(team)
        .on(member.teamId.eq(team.id))
        .transform(GroupBy.groupBy(team.country).as(list(member)));
  }

  @Override
  public Collection<MemberByCountryProjection> findAllWithCountry() {

    QMemberByCountryProjection selectExpression = new QMemberByCountryProjection(member, team);

    return from(member)
        .select(selectExpression)
        .leftJoin(team)
        .on(member.teamId.eq(team.id))
        .fetch();
  }
}
