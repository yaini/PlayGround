package com.yaini.domain.service;

import com.yaini.domain.model.Student;
import com.yaini.domain.query.GetStudentQuery;
import com.yaini.port.in.GetStudentUseCase;
import com.yaini.port.out.StudentDataProvider;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GetStudentService implements GetStudentUseCase {

  private final StudentDataProvider dataProvider;

  @Override
  public Student execute(final @Valid GetStudentQuery query) {
    return dataProvider.findByStudentNumber(query.getStudentNumber()).orElse(new Student());
  }
}
