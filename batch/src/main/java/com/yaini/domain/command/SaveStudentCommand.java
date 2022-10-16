package com.yaini.domain.command;

import com.yaini.adapter.in.batch.item.enumerated.GenderType;
import com.yaini.domain.model.Student;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveStudentCommand {
  @Min(1)
  private Long id;

  @NotBlank private String studentNumber;
  @NotBlank private String name;
  @NotNull private GenderType gender;
  @NotNull private Boolean graduation;
  @PastOrPresent private LocalDateTime admission;

  public Student getStudent() {
    return Student.builder()
        .id(this.id)
        .studentNumber(this.studentNumber)
        .name(this.name)
        .gender(this.gender)
        .graduation(this.graduation)
        .admission(this.admission)
        .build();
  }
}
