package com.yaini.domain.query;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetStudentQuery {
  @NotBlank private String studentNumber;
}
