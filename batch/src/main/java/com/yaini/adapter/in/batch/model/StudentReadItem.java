package com.yaini.adapter.in.batch.model;

import com.yaini.adapter.in.batch.model.enumerated.GenderType;
import com.yaini.adapter.in.batch.model.enumerated.GraduationType;
import java.time.LocalDateTime;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class StudentReadItem {
	private String studentNumber;
	private String name;
	private GenderType gender;
	private GraduationType graduation;
	private LocalDateTime admission;
}
