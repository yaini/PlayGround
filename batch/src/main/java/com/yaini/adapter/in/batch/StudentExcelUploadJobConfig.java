package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.chunk.processor.StudentProcessor;
import com.yaini.adapter.in.batch.chunk.writer.StudentWriter;
import com.yaini.adapter.in.batch.item.StudentReadItem;
import com.yaini.adapter.in.batch.item.StudentWriteItem;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@RequiredArgsConstructor
@Configuration
public class StudentExcelUploadJobConfig {

  private static final String PROPERTY_EXCEL_SOURCE_FILE_PATH =
      "excel.to.database.job.source.file.path";

  private final StudentProcessor studentProcessor;
  private final StudentWriter studentWriter;

  @Bean
  ItemReader<StudentReadItem> excelStudentReader(Environment environment) {
    PoiItemReader<StudentReadItem> reader = new PoiItemReader<>();
    reader.setLinesToSkip(1);
    reader.setResource(
        new ClassPathResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
    reader.setRowMapper(excelRowMapper());
    return reader;
  }

  private RowMapper<StudentReadItem> excelRowMapper() {
    BeanWrapperRowMapper<StudentReadItem> rowMapper = new BeanWrapperRowMapper<>();
    rowMapper.setTargetType(StudentReadItem.class);
    return rowMapper;
  }

  @Bean
  Step excelFileToDatabaseStep(
      ItemReader<StudentReadItem> excelStudentReader, StepBuilderFactory stepBuilderFactory) {
    return stepBuilderFactory
        .get("excelFileToDatabaseStep")
        .<StudentReadItem, StudentWriteItem>chunk(100)
        .reader(excelStudentReader)
        .processor(studentProcessor)
        .writer(studentWriter)
        .build();
  }

  @Bean
  Job excelFileToDatabaseJob(
      JobBuilderFactory jobBuilderFactory,
      @Qualifier("excelFileToDatabaseStep") Step excelStudentStep) {
    return jobBuilderFactory
        .get("excelFileToDatabaseJob")
        .incrementer(new RunIdIncrementer())
        .flow(excelStudentStep)
        .end()
        .build();
  }
}
