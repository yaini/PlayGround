package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ColumnMapRowMapper;

@RequiredArgsConstructor
@Configuration
public class PartitionJobConfig {

  public static final String JOB_NAME = "PARTITION_JOB";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final DataSource dataSource;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory
        .get(JOB_NAME)
        .incrementer(new RunIdIncrementer())
        .start(new TaskletStep())
        .build();
  }

  @Bean
  public JdbcPagingItemReader<CustomerItem> pagingItemReader() {
    JdbcPagingItemReader<CustomerItem> reader = new JdbcPagingItemReader<>();

    reader.setDataSource(this.dataSource);
    reader.setFetchSize(1000);
    reader.setRowMapper(new ColumnMapRowMapper());

    MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
    provider.setSelectClause("id, name, birth");
    provider.setFromClause("from customer");

    Map<String, Order> sortKey = new HashMap<>(1);
    sortKey.put("id", Order.ASCENDING);

    provider.setSortKeys(sortKey);

    return reader;
  }

  @Bean
  public JdbcBatchItemWriter<CustomerItem> batchItemWriter() {
    JdbcBatchItemWriter<CustomerItem> itemWriter = new JdbcBatchItemWriter<>();

    itemWriter.setDataSource(this.dataSource);
    itemWriter.setSql("insert into customer_copy values (:id, :name, :birth)");
    itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    itemWriter.afterPropertiesSet();

    return itemWriter;
  }
}
