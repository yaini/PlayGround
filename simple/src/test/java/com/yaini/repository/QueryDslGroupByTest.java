package com.yaini.repository;

import com.yaini.entity.MemberEntity;
import com.yaini.entity.TeamEntity;
import com.yaini.projection.MemberByCountryProjection;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest
public class QueryDslGroupByTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @Transactional
    public void 나라별로_팀의_멤버들을_그룹핑할_수_있다() {
        // given
        createMember(3, createTeam("Korea"));
        createMember(3, createTeam("Japan"));

        // when
        Map<String, List<MemberEntity>> actual = memberRepository.groupByCountry();

        // then
        assertEquals(actual.get("Korea").size(), 3);
        assertEquals(actual.get("Japan").size(), 3);
    }

    @Test
    @Transactional
    public void 프로젝션을_사용하여_나라와_함께_팀의_멤버들을_조회_할_수_있다() {
        // given
        createMember(3, createTeam("Korea"));
        createMember(3, createTeam("Japan"));

        // when
        Map<String, List<MemberEntity>> actual = memberRepository.findAllWithCountry()
                .stream()
                .collect(Collectors.groupingBy(
                        MemberByCountryProjection::getCountry,
                        HashMap::new,
                        Collectors.mapping(MemberByCountryProjection::getMember, Collectors.toList())));

        // then
        assertEquals(actual.get("Korea").size(), 3);
        assertEquals(actual.get("Japan").size(), 3);
    }

    private TeamEntity createTeam(final String country) {

        TeamEntity entity = TeamEntity.builder()
                .name(country + "Team")
                .country(country)
                .build();

        return teamRepository.save(entity);
    }

    private Collection<MemberEntity> createMember(final int size, final TeamEntity team) {

        List<MemberEntity> entities = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            MemberEntity entity = MemberEntity.builder()
                    .name(team.getName() + "'s " + "member")
                    .teamId(team.getId())
                    .build();

            entities.add(memberRepository.save(entity));
        }

        return entities;
    }

}
