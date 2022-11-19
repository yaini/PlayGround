package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.item.CustomerItem;
import com.yaini.adapter.in.batch.mapper.CustomerFieldSetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FlatFileJobConfig {

  public static final String JOB_NAME = "FLAT_FILE_JOB";
  public static final String FILE_PATH = "./";
  public static final String FILE_NAME = "customer.csv";
  public static final int CHUNK_SIZE = 2;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean(JOB_NAME)
  public Job flatFileJob() {

    return jobBuilderFactory.get(JOB_NAME).start(flatFileStep()).build();
  }

  @Bean
  public Step flatFileStep() {

    return stepBuilderFactory
        .get("flatFileStep")
        .<CustomerItem, CustomerItem>chunk(CHUNK_SIZE)
        .reader(flatFileItemReader())
        .writer(flatFileItemWriter())
        .build();
  }

  @Bean
  public ItemReader<CustomerItem> flatFileItemReader() {

    return new FlatFileItemReaderBuilder<CustomerItem>()
        .name("flatFileItemReader")
        .resource(new ClassPathResource(FILE_PATH + FILE_NAME))
        .linesToSkip(1)
        .fieldSetMapper(new CustomerFieldSetMapper())
        .lineTokenizer(new DelimitedLineTokenizer())
        .build();
  }

  @Bean
  public ItemWriter<CustomerItem> flatFileItemWriter() {

    return new FlatFileItemWriterBuilder<CustomerItem>()
        .name("flatFileItemWriter")
        .resource(new FileSystemResource(FILE_PATH + "new_" + FILE_NAME))
        .delimited()
        .delimiter("|")
        .names(new String[] {"id", "name", "birth"})
        .build();
  }
}
