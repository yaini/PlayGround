package com.yaini.batch;

import com.yaini.batch.processor.StudentProcessor;
import com.yaini.batch.writer.StudentWriter;
import com.yaini.domain.model.Student;
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
public class BatchConfig {

    private static final String PROPERTY_EXCEL_SOURCE_FILE_PATH = "excel.to.database.job.source.file.path";

    private final StudentProcessor studentProcessor;
    private final StudentWriter studentWriter;

    @Bean
    ItemReader<Student> excelStudentReader(Environment environment) {
        PoiItemReader<Student> reader = new PoiItemReader<>();
        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource(environment.getRequiredProperty(PROPERTY_EXCEL_SOURCE_FILE_PATH)));
        reader.setRowMapper(excelRowMapper());
        return reader;
    }

    private RowMapper<Student> excelRowMapper() {
        BeanWrapperRowMapper<Student> rowMapper = new BeanWrapperRowMapper<>();
        rowMapper.setTargetType(Student.class);
        return rowMapper;
    }

    @Bean
    Step excelFileToDatabaseStep(ItemReader<Student> excelStudentReader,
                                 StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("excelFileToDatabaseStep")
                .<Student, Student>chunk(1)
                .reader(excelStudentReader)
                .processor(studentProcessor)
                .writer(studentWriter)
                .build();
    }

    @Bean
    Job excelFileToDatabaseJob(JobBuilderFactory jobBuilderFactory,
                               @Qualifier("excelFileToDatabaseStep") Step excelStudentStep) {
        return jobBuilderFactory.get("excelFileToDatabaseJob")
                .incrementer(new RunIdIncrementer())
                .flow(excelStudentStep)
                .end()
                .build();
    }
}
