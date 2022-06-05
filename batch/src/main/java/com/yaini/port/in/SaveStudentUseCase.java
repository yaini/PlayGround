package com.yaini.port.in;

import com.yaini.domain.command.SaveStudentCommand;
import com.yaini.domain.model.Student;
import java.util.Collection;

public interface SaveStudentUseCase {

    Collection<Student> execute(final Collection<SaveStudentCommand> command);
}
