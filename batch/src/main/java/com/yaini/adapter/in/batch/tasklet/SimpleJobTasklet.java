package com.yaini.adapter.in.batch.tasklet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class SimpleJobTasklet implements Tasklet {

  @Override
  public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext)
      throws Exception {

    log.info("jobName = {}", chunkContext.getStepContext().getJobName());
    return RepeatStatus.FINISHED;
  }
}
