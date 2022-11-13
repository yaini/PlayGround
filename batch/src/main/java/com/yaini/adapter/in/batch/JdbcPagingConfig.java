package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@RequiredArgsConstructor
@Configuration
public class JdbcPagingConfig {

  public static final String JOB_NAME = "JDBC_PAGING_JOB";
  public static final int CHUNK_SIZE = 100;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final DataSource dataSource;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory.get(JOB_NAME).start(jdbcPagingStep()).build();
  }

  @Bean
  public Step jdbcPagingStep() {

    return stepBuilderFactory
        .get("jdbcPagingStep")
        .chunk(CHUNK_SIZE)
        .reader(jdbcPagingItemReader())
        .build();
  }

  @Bean
  public JdbcPagingItemReader<CustomerItem> jdbcPagingItemReader() {
    JdbcPagingItemReader<CustomerItem> reader = new JdbcPagingItemReader<>();

    reader.setDataSource(this.dataSource);
	reader.setPageSize(CHUNK_SIZE);
    reader.setRowMapper(new BeanPropertyRowMapper<>(CustomerItem.class));

    MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
    provider.setSelectClause("id, name, birth");
    provider.setFromClause("from customer");

    Map<String, Order> sortKey = new HashMap<>(1);
    sortKey.put("id", Order.ASCENDING);

    provider.setSortKeys(sortKey);

    return reader;
  }
}
