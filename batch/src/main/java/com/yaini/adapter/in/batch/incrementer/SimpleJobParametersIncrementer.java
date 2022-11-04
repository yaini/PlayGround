package com.yaini.adapter.in.batch.incrementer;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class SimpleJobParametersIncrementer implements JobParametersIncrementer {

  private final SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd-hhmmss");

  @Override
  public JobParameters getNext(final JobParameters parameters) {

    String id = simpleDate.format(new Date());

    return new JobParametersBuilder().addString("run.id", id).toJobParameters();
  }
}
