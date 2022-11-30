package com.yaini.adapter.in.batch.mapper;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CustomerFieldSetMapper implements FieldSetMapper<CustomerItem> {

  @Override
  public CustomerItem mapFieldSet(final FieldSet fieldSet) throws BindException {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    return CustomerItem.builder()
        .id(fieldSet.readLong(0))
        .name(fieldSet.readString(1))
        .birth(LocalDate.parse(fieldSet.readString(2), formatter))
        .build();
  }
}
