package com.yaini.port.in;

import com.yaini.domain.command.SaveStudentCommand;
import com.yaini.domain.model.Student;

import javax.validation.Valid;
import java.util.Collection;

public interface SaveStudentUseCase {

    Collection<Student> execute(final @Valid Collection<SaveStudentCommand> command);
}
