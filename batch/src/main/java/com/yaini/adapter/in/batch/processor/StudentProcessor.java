package com.yaini.adapter.in.batch.processor;

import com.yaini.adapter.in.batch.model.StudentReadItem;
import com.yaini.adapter.in.batch.model.StudentWriteItem;
import com.yaini.adapter.in.batch.model.enumerated.GraduationType;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@RequiredArgsConstructor
@Component
public class StudentProcessor implements ItemProcessor<StudentReadItem, StudentWriteItem> {

    @Override
    public StudentWriteItem process(final StudentReadItem item) {
        // TODO constraints (e.g. studentNumber로 id 가져오기)

        return StudentWriteItem.builder()
                .studentNumber(item.getStudentNumber())
                .name(item.getName())
                .gender(item.getGender())
                .graduation(item.getGraduation().equals(GraduationType.Y))
                .admission(item.getAdmission())
                .build()
                .validate();
    }
}
