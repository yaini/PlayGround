package com.yaini.adapter.in.batch.item;

import java.time.LocalDateTime;
import javax.persistence.Entity;

@Entity
public class CustomerItem {
  private Long id;
  private String name;
  private LocalDateTime birth;
}
