package com.yaini.adapter.in.batch.chunk.writer;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerWriter implements ItemWriter<CustomerItem> {

  @Override
  public void write(final List<? extends CustomerItem> items) {

    items.forEach(i -> log.info("Received the information of a customer: {}", i));
  }
}
