package com.yaini.adapter.in.batch.tasklet.chunk.processor;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.util.Map;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.classify.Classifier;

@SuppressWarnings("unchecked")
public class CustomerProcessorClassifier<C, T> implements Classifier<C, T> {

  private final Map<Integer, ItemProcessor<CustomerItem, CustomerItem>> processorMap;

  public CustomerProcessorClassifier(
      final Map<Integer, ItemProcessor<CustomerItem, CustomerItem>> processorMap) {
    this.processorMap = processorMap;
  }

  @Override
  public T classify(final C classifiable) {
    return (T) processorMap.get(((CustomerItem) classifiable).getBirth().getYear());
  }
}
