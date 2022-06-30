package com.yaini.service;

import com.yaini.converter.SimpleEntityConverter;
import com.yaini.entity.SimpleEntity;
import com.yaini.model.Simple;
import com.yaini.repository.SimpleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class SimpleService {

    private final SimpleRepository repository;

    public Simple findOne(final Long id) {
        return SimpleEntityConverter.to(repository.getById(id));
    }

    public void create() {
        SimpleEntity entity = SimpleEntity.builder()
                .name("name")
                .build();

        repository.save(entity);
    }

    public void delete(final Long id) {
        repository.delete(repository.getById(id));
    }

}
