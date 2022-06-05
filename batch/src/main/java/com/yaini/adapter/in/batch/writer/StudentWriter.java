package com.yaini.adapter.in.batch.writer;

import com.yaini.adapter.in.batch.converter.StudentItemConverter;
import com.yaini.adapter.in.batch.model.StudentWriteItem;
import com.yaini.domain.command.SaveStudentCommand;
import com.yaini.domain.model.Student;
import com.yaini.port.in.SaveStudentUseCase;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class StudentWriter implements ItemWriter<StudentWriteItem> {

    private final SaveStudentUseCase saveStudentUseCase;

    @Override
    public void write(final List<? extends StudentWriteItem> items) {
        // items to command
        Collection<SaveStudentCommand> commands = items.stream()
                .map(StudentItemConverter::from)
                .collect(Collectors.toUnmodifiableList());

        Collection<Student> data = saveStudentUseCase.execute(commands);
        data.forEach(i -> log.info("Received the information of a student: {}", i));
    }
}
