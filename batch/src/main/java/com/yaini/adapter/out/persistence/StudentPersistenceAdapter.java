package com.yaini.adapter.out.persistence;

import com.yaini.domain.model.Student;
import com.yaini.port.out.StudentDataProvider;
import java.util.Collection;

public class StudentPersistenceAdapter implements StudentDataProvider {

	@Override
	public Collection<Student> saveAll(final Collection<Student> data) {
		return null;
	}
}
