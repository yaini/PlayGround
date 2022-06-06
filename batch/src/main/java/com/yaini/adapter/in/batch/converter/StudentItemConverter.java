package com.yaini.adapter.in.batch.converter;

import com.yaini.adapter.in.batch.item.StudentWriteItem;
import com.yaini.domain.command.SaveStudentCommand;

public class StudentItemConverter {

    private StudentItemConverter() {
        throw new UnsupportedOperationException();
    }

    public static SaveStudentCommand from(final StudentWriteItem item) {
        return SaveStudentCommand.builder()
                .id(item.getId())
                .studentNumber(item.getStudentNumber())
                .name(item.getName())
                .gender(item.getGender())
                .graduation(item.getGraduation())
                .admission(item.getAdmission())
                .build();
    }

}
