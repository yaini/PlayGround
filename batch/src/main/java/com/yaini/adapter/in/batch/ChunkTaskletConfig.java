package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.item.CustomerItem;
import com.yaini.adapter.in.batch.tasklet.chunk.processor.CustomerProcessor;
import com.yaini.adapter.in.batch.tasklet.chunk.reader.CustomerReader;
import com.yaini.adapter.in.batch.tasklet.chunk.writer.CustomerWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ChunkTaskletConfig {

  public static final String JOB_NAME = "CHUNK_TASKLET_JOB";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory.get(JOB_NAME).start(chunkStep()).build();
  }

  @Bean
  public Step chunkStep() {

    return stepBuilderFactory
        .get("chunkStep")
        .<CustomerItem, CustomerItem>chunk(2)
        .reader(customerItemReader())
        .processor(customerItemProcessor())
        .writer(customerItemWriter())
        .build();
  }

  @Bean
  public ItemReader<CustomerItem> customerItemReader() {

    List<CustomerItem> items = new ArrayList<>();
    items.add(new CustomerItem(1L, "customer1", LocalDate.now()));
    items.add(new CustomerItem(2L, "customer2", LocalDate.now()));
    items.add(new CustomerItem(3L, "customer3", LocalDate.now()));

    return new CustomerReader(items);
  }

  @Bean
  public ItemProcessor<CustomerItem, CustomerItem> customerItemProcessor() {

    return new CustomerProcessor();
  }

  @Bean
  public ItemWriter<CustomerItem> customerItemWriter() {

    return new CustomerWriter();
  }
}
