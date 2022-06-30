package com.yaini.repository;

import com.yaini.entity.SimpleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class JPASoftDeleteTest {

    @Autowired
    private SimpleRepository repository;

    @Test
    public void 영속성_컨텍스트의_엔티티를_조회하면_SELECT_쿼리가_실행되지_않는다() {

        // given
        SimpleEntity saved = create("simpleName");

        // when
        repository.delete(saved);

        // then
        Optional<SimpleEntity> deleted = repository.findById(saved.getId());
        assertEquals(deleted, Optional.empty());
        assertEquals(saved.getDeleted(), true);

    }

    private SimpleEntity create(String name) {
        SimpleEntity entity = SimpleEntity.builder()
                .name(name)
                .build();

        return repository.save(entity);
    }
}
