package com.yaini.adapter.in.batch.item;

import com.yaini.adapter.in.batch.item.enumerated.GenderType;
import com.yaini.adapter.in.batch.support.SelfValidatable;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class StudentWriteItem extends SelfValidatable<StudentWriteItem> {
  @Min(1)
  private Long id;

  @NotBlank private String studentNumber;
  @NotBlank private String name;
  @NotNull private GenderType gender;
  @NotNull private Boolean graduation;
  @PastOrPresent private LocalDateTime admission;

  @Override
  public StudentWriteItem validate() {
    super.validateSelf(this);
    return this;
  }
}
