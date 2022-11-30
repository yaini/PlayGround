package com.yaini.adapter.in.batch.chunk.processor;

import com.yaini.adapter.in.batch.item.CustomerItem;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerProcessor implements ItemProcessor<CustomerItem, CustomerItem> {

  @Override
  public CustomerItem process(final CustomerItem item) {

    return CustomerItem.builder()
        .id(item.getId())
        .name(item.getName().toLowerCase())
        .birth(item.getBirth())
        .build();
  }
}
