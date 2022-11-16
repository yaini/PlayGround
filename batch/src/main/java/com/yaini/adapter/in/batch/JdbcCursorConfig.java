package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.item.CustomerItem;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JdbcCursorConfig {

  public static final String JOB_NAME = "JDBC_CURSOR_JOB";
  public static final int CHUNK_SIZE = 100;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final DataSource dataSource;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory.get(JOB_NAME).start(jdbcCursorStep()).build();
  }

  @Bean
  public Step jdbcCursorStep() {

    return stepBuilderFactory
        .get("jdbcCursorStep")
        .<CustomerItem, CustomerItem>chunk(CHUNK_SIZE)
        .reader(jdbcCursorItemReader())
        .writer(jdbcBatchItemWriter())
        .build();
  }

  @Bean
  public ItemReader<CustomerItem> jdbcCursorItemReader() {

    return new JdbcCursorItemReaderBuilder<CustomerItem>()
        .name("jdbcCursorItemReader")
        .fetchSize(CHUNK_SIZE)
        .sql("select id, name, date from customer")
        .beanRowMapper(CustomerItem.class)
        .dataSource(this.dataSource)
        .build();
  }

  @Bean
  public ItemWriter<CustomerItem> jdbcBatchItemWriter() {

    return new JdbcBatchItemWriterBuilder<CustomerItem>()
            .dataSource(this.dataSource)
            .sql("INSERT INTO customer_copy VALUES (:id, :name, :date)")
            .beanMapped()
            .build();
  }
}
