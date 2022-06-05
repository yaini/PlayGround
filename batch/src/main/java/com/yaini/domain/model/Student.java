package com.yaini.domain.model;

import com.yaini.adapter.in.batch.model.enumerated.GenderType;
import java.time.LocalDateTime;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class Student {
    private Long id;
    private String studentNumber;
    private String name;
    private GenderType gender;
    private Boolean graduation;
    private LocalDateTime admission;
}
