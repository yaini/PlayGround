package com.yaini.port.out;

import com.yaini.domain.model.Student;
import java.util.Collection;

public interface StudentDataProvider {

	Collection<Student> saveAll(final Collection<Student> data);

}
