package com.yaini.adapter.out.persistence;

import com.yaini.adapter.out.persistence.converter.StudentEntityConverter;
import com.yaini.adapter.out.persistence.entity.StudentEntity;
import com.yaini.adapter.out.persistence.repository.StudentRepository;
import com.yaini.domain.model.Student;
import com.yaini.port.out.StudentDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class StudentPersistenceAdapter implements StudentDataProvider {

    private final StudentRepository repository;

    @Override
    public Collection<Student> saveAll(final Collection<Student> data) {
        Collection<StudentEntity> entities = data.stream()
                .map(StudentEntityConverter::to)
                .collect(Collectors.toUnmodifiableList());

        return repository.saveAll(entities).stream()
                .map(StudentEntityConverter::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Student> findByStudentNumber(final String studentNumber) {
        return repository.findByStudentNumber(studentNumber)
                .map(StudentEntityConverter::from);
    }
}
