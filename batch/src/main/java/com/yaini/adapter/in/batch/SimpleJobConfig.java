package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.incrementer.SimpleJobParametersIncrementer;
import com.yaini.adapter.in.batch.tasklet.SimpleJobTasklet;
import com.yaini.adapter.in.batch.validator.SimpleJobParameterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
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
        .start(startStep("scopeTest"))
        .next(nextStep())
        .validator(new SimpleJobParameterValidator())
        // .validator(new DefaultJobParametersValidator(new String[]{"name"}, new String[]{"type"}))
        // .preventRestart()
        .incrementer(new SimpleJobParametersIncrementer())
        .build();
  }

  @Bean
  @JobScope
  public Step startStep(@Value("#{jobParameters['jobScopeString']}") String jobScopeString) {

    log.info("jobScopeString = {}", jobScopeString);

    return stepBuilderFactory
        .get("startStep")
        .tasklet(new SimpleJobTasklet())
        .startLimit(10)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Step nextStep() {

    return stepBuilderFactory
        .get("nextStep")
        .tasklet(((contribution, chunkContext) -> RepeatStatus.FINISHED))
        .build();
  }
}
