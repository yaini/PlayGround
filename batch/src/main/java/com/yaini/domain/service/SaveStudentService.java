package com.yaini.domain.service;

import com.yaini.domain.command.SaveStudentCommand;
import com.yaini.domain.model.Student;
import com.yaini.port.in.SaveStudentUseCase;
import com.yaini.port.out.StudentDataProvider;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
@Service
public class SaveStudentService implements SaveStudentUseCase {

    private final StudentDataProvider dataProvider;
    @Override
    public Collection<Student> execute(final Collection<@Valid SaveStudentCommand> command) {
        Collection<Student> target = command.stream()
                .map(SaveStudentCommand::getStudent)
                .collect(Collectors.toUnmodifiableList());

        return dataProvider.saveAll(target);
    }

}
