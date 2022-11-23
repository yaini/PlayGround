package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@RequiredArgsConstructor
@Configuration
public class MultiThreadJobConfig {

  public static final String JOB_NAME = "MULTI_THREAD_JOB";
  public static final int CHUNK_SIZE = 100;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory.get(JOB_NAME).start(jpaPagingStep()).build();
  }

  @Bean
  public Step jpaPagingStep() {

    return stepBuilderFactory
        .get("multiThreadStep")
        .<CustomerItem, CustomerItem>chunk(CHUNK_SIZE)
        .reader(jpaPagingItemReader())
        .writer(tempJpaPagingWriter())
        .taskExecutor(executor())
        .build();
  }

  @Bean
  public JpaPagingItemReader<CustomerItem> jpaPagingItemReader() {

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("name", "a%");

    return new JpaPagingItemReaderBuilder<CustomerItem>()
        .name("multiThreadJpaCursorItemReader")
        .entityManagerFactory(this.entityManagerFactory)
        .pageSize(CHUNK_SIZE)
        .queryString("select c from customer c where name like :name")
        .parameterValues(parameters)
        .build();
  }

  @Bean
  public ItemWriter<CustomerItem> tempJpaPagingWriter() {

    return new JpaItemWriterBuilder<CustomerItem>()
        .usePersist(true)
        .entityManagerFactory(this.entityManagerFactory)
        .build();
  }

  @Bean
  public TaskExecutor executor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

    taskExecutor.setCorePoolSize(4);
    taskExecutor.setMaxPoolSize(8);
    taskExecutor.setThreadNamePrefix("ASYNC-THREAD");

    return taskExecutor;
  }
}
