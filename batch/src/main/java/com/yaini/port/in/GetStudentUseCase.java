package com.yaini.port.in;

import com.yaini.domain.model.Student;
import com.yaini.domain.query.GetStudentQuery;
import javax.validation.Valid;

public interface GetStudentUseCase {

  Student execute(final @Valid GetStudentQuery query);
}
