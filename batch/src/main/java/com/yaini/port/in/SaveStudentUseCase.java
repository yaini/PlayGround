package com.yaini.port.in;

import com.yaini.domain.model.Student;

public interface SaveStudentUseCase {

    Student execute(final Student student);
}
