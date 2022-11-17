package com.yaini.adapter.in.batch.tasklet.chunk.reader;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class CustomerReader implements ItemReader<CustomerItem> {

  private List<CustomerItem> items;

  public CustomerReader(final List<CustomerItem> items) {
    this.items = new ArrayList<>(items);
  }

  @Override
  public CustomerItem read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

    if (!items.isEmpty()) {
      return items.remove(0);
    }

    return null;
  }
}
