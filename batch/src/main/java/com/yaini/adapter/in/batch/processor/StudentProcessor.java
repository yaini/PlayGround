package com.yaini.adapter.in.batch.processor;

import com.yaini.adapter.in.batch.item.StudentReadItem;
import com.yaini.adapter.in.batch.item.StudentWriteItem;
import com.yaini.adapter.in.batch.item.enumerated.GenderType;
import com.yaini.adapter.in.batch.item.enumerated.GraduationType;
import com.yaini.domain.query.GetStudentQuery;
import com.yaini.port.in.GetStudentUseCase;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@RequiredArgsConstructor
@Component
public class StudentProcessor implements ItemProcessor<StudentReadItem, StudentWriteItem> {

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy HH:mm");
  private final GetStudentUseCase getStudentUseCase;

  @Override
  public StudentWriteItem process(final StudentReadItem item) {

    Long id = getStudentUseCase.execute(new GetStudentQuery(item.getStudentNumber())).getId();

    return StudentWriteItem.builder()
        .id(id)
        .studentNumber(item.getStudentNumber())
        .name(item.getName())
        .gender(GenderType.valueOf(item.getGender()))
        .graduation(GraduationType.valueOf(item.getGraduation()).equals(GraduationType.Y))
        .admission(LocalDateTime.parse(item.getAdmission(), formatter))
        .build()
        .validate();
  }
}
