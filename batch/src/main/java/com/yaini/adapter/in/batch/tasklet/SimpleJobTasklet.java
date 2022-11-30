package com.yaini.adapter.in.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@StepScope
public class SimpleJobTasklet implements Tasklet {

  @Value("#{jobParameters['stepScopeString']}") private String stepScopeString;

  @Override
  public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext)
      throws Exception {

    log.info("stepScopeString = {}", stepScopeString);
    return RepeatStatus.FINISHED;
  }
}
