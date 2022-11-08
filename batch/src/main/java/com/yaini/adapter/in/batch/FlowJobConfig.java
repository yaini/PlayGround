package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.tasklet.SimpleJobTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FlowJobConfig {

  public static final String JOB_NAME = "FLOW_JOB";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory.get(JOB_NAME).start(startFlow()).end().build();
  }

  public Flow startFlow() {
    FlowBuilder<Flow> flowJobBuilder = new FlowBuilder<>("startFlow");

    return flowJobBuilder.start(startStep()).next(nextStep()).end();
  }

  @Bean
  public Step startStep() {

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
