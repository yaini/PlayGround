package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.listener.PassCheckingListener;
import com.yaini.adapter.in.batch.tasklet.SimpleJobTasklet;
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
public class FlowJobConfig {

  public static final String JOB_NAME = "FLOW_JOB";

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory
        .get(JOB_NAME)
        .start(flowStartStep())
        .on("FAILED")
        .to(flowToStep())
        .on("PASS")
        .stop()
        .from(flowStartStep())
        .on("*")
        .to(flowToStep())
        .next(flowNextStep())
        .on("FAILED")
        .end()
        .end()
        .build();
  }

  @Bean
  public Step flowStartStep() {

    return stepBuilderFactory
        .get("flowStartStep")
        .tasklet(new SimpleJobTasklet())
        .startLimit(10)
        .allowStartIfComplete(true)
        .build();
  }

  @Bean
  public Step flowToStep() {

    return stepBuilderFactory
        .get("flowToStep")
        .tasklet(((contribution, chunkContext) -> RepeatStatus.FINISHED))
        .listener(new PassCheckingListener())
        .build();
  }

  @Bean
  public Step flowNextStep() {

    return stepBuilderFactory
        .get("flowNextStep")
        .tasklet(((contribution, chunkContext) -> RepeatStatus.FINISHED))
        .build();
  }
}
