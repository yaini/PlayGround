package com.yaini.service;

import com.yaini.converter.SimpleEntityConverter;
import com.yaini.entity.SimpleEntity;
import com.yaini.model.Simple;
import com.yaini.repository.SimpleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SimpleService {

    private final SimpleRepository repository;

    public Simple simpleJpaTest() {

        SimpleEntity entity = SimpleEntity.builder()
                .name("simple")
                .build();

        return SimpleEntityConverter.to(repository.save(entity));

    }
}
