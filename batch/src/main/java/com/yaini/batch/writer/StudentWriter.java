package com.yaini.batch.writer;

import com.yaini.domain.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StudentWriter implements ItemWriter<Student> {

    @Override
    public void write(final List<? extends Student> items) {
        items.forEach(i -> log.info("Received the information of a student: {}", i));
    }
}
