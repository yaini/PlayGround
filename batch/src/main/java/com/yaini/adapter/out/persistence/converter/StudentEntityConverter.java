package com.yaini.adapter.out.persistence.converter;

import com.yaini.adapter.out.persistence.entity.StudentEntity;
import com.yaini.domain.model.Student;

public class StudentEntityConverter {

    private StudentEntityConverter() {
        throw new UnsupportedOperationException();
    }

    public static StudentEntity to(final Student data) {
        return StudentEntity.builder()
                .id(data.getId())
                .studentNumber(data.getStudentNumber())
                .name(data.getName())
                .gender(data.getGender())
                .graduation(data.getGraduation())
                .admission(data.getAdmission())
                .build();
    }

    public static Student from(final StudentEntity entity) {
        return Student.builder()
                .id(entity.getId())
                .studentNumber(entity.getStudentNumber())
                .name(entity.getName())
                .gender(entity.getGender())
                .graduation(entity.getGraduation())
                .admission(entity.getAdmission())
                .build();
    }
}
