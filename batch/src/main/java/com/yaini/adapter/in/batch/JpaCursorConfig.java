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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JpaCursorConfig {

  public static final String JOB_NAME = "JPA_CURSOR_JOB";
  public static final int CHUNK_SIZE = 100;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory.get(JOB_NAME).start(jdbcStep()).build();
  }

  @Bean
  public Step jdbcStep() {

    return stepBuilderFactory
        .get("jpaStep")
        .<CustomerItem, CustomerItem>chunk(CHUNK_SIZE)
        .reader(jpaCursorItemReader())
        .writer(jpaItemWriter())
        .build();
  }

  @Bean
  public ItemReader<CustomerItem> jpaCursorItemReader() {

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("name", "a%");

    return new JpaCursorItemReaderBuilder<CustomerItem>()
        .name("jpaCursorItemReader")
        .entityManagerFactory(this.entityManagerFactory)
        .queryString("select c from customer c where name like :name")
        .parameterValues(parameters)
        .build();
  }

  @Bean
  public ItemWriter<CustomerItem> jpaItemWriter() {

    return new JpaItemWriterBuilder<CustomerItem>()
            .usePersist(true)
            .entityManagerFactory(this.entityManagerFactory)
            .build();
  }
}
