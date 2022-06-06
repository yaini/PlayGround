package com.yaini.domain.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class GetStudentQuery {
    @NotBlank
    private String studentNumber;
}
