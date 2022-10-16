package com.yaini.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.yaini.entity.SimpleEntity;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class JPAExistsQueryTest {

  @Autowired private SimpleRepository repository;

  @Test
  @Transactional
  public void SpringDataJpaRepository의_exist함수를_실행한다() {
    // given
    SimpleEntity entity = SimpleEntity.builder().name("existTest").build();

    repository.save(entity);

    // when
    boolean actual = repository.existsByNameCustom(entity.getName());

    // then
    assertTrue(actual);
  }

  @Test
  @Transactional
  public void QueryDsl의_exist함수를_실행한다() {
    // given
    SimpleEntity entity = SimpleEntity.builder().name("existTest").build();

    repository.save(entity);

    // when
    boolean actual = repository.existsByNameCustom(entity.getName());

    // then
    assertTrue(actual);
  }
}
