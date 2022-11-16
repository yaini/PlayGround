package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JpaPagingConfig {

  public static final String JOB_NAME = "JPA_PAGING_JOB";
  public static final int CHUNK_SIZE = 100;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final DataSource dataSource;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory.get(JOB_NAME).start(jpaPagingStep()).build();
  }

  @Bean
  public Step jpaPagingStep() {

    return stepBuilderFactory
        .get("jpaPagingStep")
        .chunk(CHUNK_SIZE)
        .reader(jpaPagingItemReader())
        .writer(tempJpaPagingWriter())
        .build();
  }

  @Bean
  public JpaPagingItemReader<CustomerItem> jpaPagingItemReader() {

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("name", "a%");

    return new JpaPagingItemReaderBuilder<CustomerItem>()
        .name("jpaCursorItemReader")
        .entityManagerFactory(this.entityManagerFactory)
        .pageSize(CHUNK_SIZE)
        .queryString("select c from customer c where name like :name")
        .parameterValues(parameters)
        .build();
  }

  @Bean
  public ItemWriter<Object> tempJpaPagingWriter() {

    return new ListItemWriter<>();
  }
}
