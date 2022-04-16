package com.yaini.repository;

import com.yaini.entity.SimpleEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class JPAPersistenceContextTest {

    @Autowired
    private SimpleRepository repository;

    @PersistenceContext
    private EntityManager em;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }


    @Test
    @Transactional
    public void 영속성_컨텍스트의_엔티티를_조회하면_SELECT_쿼리가_실행되지_않는다() {

        SimpleEntity saved = create("simpleName");

        repository.findById(saved.getId());

    }

    @Test
    @Transactional
    public void 엔티티의_값을_변경하면_UPDATE_쿼리가_실행된다() {

        SimpleEntity saved = create("simpleName");

        SimpleEntity entity = SimpleEntity.builder()
                .id(saved.getId())
                .name("change name")
                .build();

        repository.save(entity);

    }

    @Test
    @Transactional
    public void 엔티티의_값을_변경하지_않으면_UPDATE_쿼리가_실행되지_않는다() {

        SimpleEntity saved = create("simpleName");

        SimpleEntity entity = SimpleEntity.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();

        repository.save(entity);

    }

    @Test
    @Transactional
    public void saveAll을_실행해도_bulk_INSERT와_UPDATE가_실행되지_않는다() {

        List<SimpleEntity> list = new ArrayList<>();
        list.add(SimpleEntity.builder().name("simpleName").build());
        list.add(SimpleEntity.builder().name("simpleName").build());
        repository.saveAll(list);

        list.forEach(
                entity -> entity.setName("changeName")
        );

        repository.saveAll(list);
    }

    @Test
    @Transactional
    public void DELETE_BY_ID를_실행하면_각_ID마다_SELECT_쿼리가_실행된다() {

        List<SimpleEntity> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(create("deleteTest"));
            em.detach(list.get(i));
        }

        for (int i = 0; i < 2; i++) {
            repository.deleteById(list.get(i).getId());
        }

    }

    @Test
    @Transactional
    public void DELETE_ALL_BY를_실행하면_하나의_SELECT_쿼리를_통해_엔티티를_조회한다() {

        List<SimpleEntity> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(create("deleteTest"));
            em.detach(list.get(i));
        }

        repository.deleteAllByName("deleteTest");

    }


    private SimpleEntity create(String name) {
        SimpleEntity entity = SimpleEntity.builder()
                .name(name)
                .build();

        return repository.save(entity);
    }

}

