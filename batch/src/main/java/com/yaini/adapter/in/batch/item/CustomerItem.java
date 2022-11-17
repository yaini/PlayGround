package com.yaini.adapter.in.batch.item;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CustomerItem {
  private Long id;
  private String name;
  private LocalDateTime birth;
}
