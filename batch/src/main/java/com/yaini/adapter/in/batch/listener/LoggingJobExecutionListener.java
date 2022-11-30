package com.yaini.adapter.in.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class LoggingJobExecutionListener implements JobExecutionListener {

  @Override
  public void beforeJob(final JobExecution jobExecution) {
    log.info("[JobStart] {}", jobExecution.getJobInstance().getJobName());
  }

  @Override
  public void afterJob(final JobExecution jobExecution) {
    log.info("[JobEnd] {}", jobExecution.getJobInstance().getJobName());
  }
}
