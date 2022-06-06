package com.yaini.domain.model;

import com.yaini.adapter.in.batch.item.enumerated.GenderType;
import lombok.*;

import java.time.LocalDateTime;

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
