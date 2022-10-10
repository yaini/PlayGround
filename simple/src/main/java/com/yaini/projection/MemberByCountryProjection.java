package com.yaini.projection;

import com.querydsl.core.annotations.QueryProjection;
import com.yaini.entity.MemberEntity;
import com.yaini.entity.TeamEntity;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class MemberByCountryProjection implements Serializable {

  String country;
  MemberEntity member;

  @QueryProjection
  public MemberByCountryProjection(final MemberEntity member, final TeamEntity team) {
    if (member == null || team == null) {
      return;
    }

    this.member = member;
    this.country = team.getCountry();
  }
}
