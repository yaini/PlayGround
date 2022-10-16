package com.yaini.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.yaini.adapter.in.batch.item.enumerated.GenderType;
import com.yaini.adapter.out.persistence.entity.StudentEntity;
import com.yaini.adapter.out.persistence.repository.StudentRepository;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class StudentRepositoryTest {

  @Autowired private StudentRepository repository;

  @Test
  @Transactional
  public void 엔티티를_생성_후_조회한다() {

    // given
    StudentEntity entity =
        StudentEntity.builder()
            .name("name")
            .admission(LocalDateTime.now().minusDays(2))
            .graduation(true)
            .studentNumber("studentNumber")
            .gender(GenderType.F)
            .build();

    StudentEntity expected = repository.save(entity);

    // when
    StudentEntity actual = repository.findById(expected.getId()).orElse(null);

    // then
    assertEquals(expected, actual);
  }
}
