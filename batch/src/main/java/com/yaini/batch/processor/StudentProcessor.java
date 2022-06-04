package com.yaini.batch.processor;

import com.yaini.domain.model.Student;
import com.yaini.port.in.SaveStudentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StudentProcessor implements ItemProcessor<Student, Student> {

    private final SaveStudentUseCase saveStudentUseCase;

    // TODO model to item / item validation / execute(command)
    @Override
    public Student process(final Student item) {
        return saveStudentUseCase.execute(item);
    }
}
