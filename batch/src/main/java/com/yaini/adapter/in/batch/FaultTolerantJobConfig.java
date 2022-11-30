package com.yaini.adapter.in.batch;

import java.util.SplittableRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FaultTolerantJobConfig {

  public static final String JOB_NAME = "FAULT_TOLERANT_JOB";
  public static final int CHUNK_SIZE = 2;
  public static final int SKIP_LIMIT = 10;
  public static final int RETRY_LIMIT = 3;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean(JOB_NAME)
  public Job batchJob() {

    return jobBuilderFactory.get(JOB_NAME).start(faultTolerantStep()).build();
  }

  @Bean
  public Step faultTolerantStep() {

    return stepBuilderFactory
        .get("faultTolerantStep")
        .<Integer, String>chunk(CHUNK_SIZE)
        .reader(
            () -> {
              int item = new SplittableRandom().nextInt();
              log.info("[ItemReader] item :: {} ", item);
              if (item % 2 == 0) {
                return item;
              }

              throw new IllegalAccessException("[FaultTolerantJobConfig] read :: " + item);
            })
        .processor(
            (ItemProcessor<Integer, String>)
                item -> {
                  log.info("[ItemProcessor] item :: {} ", item);
                  if (item % 3 == 0) {
                    return Integer.toString(item);
                  }

                  throw new IllegalAccessException("[FaultTolerantJobConfig] process :: " + item);
                })
        .writer(
            items -> items.forEach(item -> log.info("[FaultTolerantJobConfig] write :: {} ", item)))
        .faultTolerant()
        .skip(IllegalAccessException.class)
        .skipLimit(SKIP_LIMIT)
        .retry(IllegalAccessException.class)
        .retryLimit(RETRY_LIMIT)
        .build();
  }
}
