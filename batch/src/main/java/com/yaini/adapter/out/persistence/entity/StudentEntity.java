package com.yaini.adapter.out.persistence.entity;

import com.yaini.adapter.in.batch.item.enumerated.GenderType;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@ToString
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "student_number", nullable = false, unique = true)
  private String studentNumber;

  @Column(name = "name", nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", nullable = false)
  private GenderType gender;

  @Column(name = "graduation", nullable = false)
  private Boolean graduation;

  @Column(name = "admission", nullable = false)
  private LocalDateTime admission;
}
