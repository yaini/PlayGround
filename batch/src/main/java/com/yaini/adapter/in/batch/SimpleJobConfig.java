package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.incrementer.SimpleJobParametersIncrementer;
import com.yaini.adapter.in.batch.tasklet.SimpleJobTasklet;
import com.yaini.adapter.in.batch.validator.SimpleJobParameterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SimpleJobConfig {

  public static final String JOB_NAME = "SIMPLE_JOB";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory
        .get(JOB_NAME)
        .start(startStep())
        .next(nextStep())
        .validator(new SimpleJobParameterValidator())
        // .validator(new DefaultJobParametersValidator(new String[]{"name"}, new String[]{"type"}))
        // .preventRestart()
        .incrementer(new SimpleJobParametersIncrementer())
        .build();
  }

  @Bean
  public Step startStep() {

    return stepBuilderFactory.get("startStep").tasklet(new SimpleJobTasklet()).build();
  }

  @Bean
  public Step nextStep() {

    return stepBuilderFactory
        .get("nextStep")
        .tasklet(((contribution, chunkContext) -> RepeatStatus.FINISHED))
        .build();
  }
}
