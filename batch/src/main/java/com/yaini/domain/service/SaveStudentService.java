package com.yaini.domain.service;

import com.yaini.domain.model.Student;
import com.yaini.port.in.SaveStudentUseCase;
import org.springframework.stereotype.Service;

@Service
public class SaveStudentService implements SaveStudentUseCase {

    @Override
    public Student execute(final Student student) {
        return student;
    }

}
