package com.yaini.port.in;

import com.yaini.domain.command.SaveStudentCommand;
import com.yaini.domain.model.Student;
import java.util.Collection;
import javax.validation.Valid;

public interface SaveStudentUseCase {

  Collection<Student> execute(final @Valid Collection<SaveStudentCommand> command);
}
