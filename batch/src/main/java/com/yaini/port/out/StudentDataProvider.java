package com.yaini.port.out;

import com.yaini.domain.model.Student;
import java.util.Collection;
import java.util.Optional;

public interface StudentDataProvider {

  Collection<Student> saveAll(final Collection<Student> data);

  Optional<Student> findByStudentNumber(final String studentNumber);
}
