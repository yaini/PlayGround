package com.yaini.job;

import com.yaini.adapter.in.batch.SimpleJobConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@EnableAutoConfiguration
@EnableBatchProcessing
@SpringBatchTest
@SpringBootTest(
    classes = {SimpleJobConfig.class},
    webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SimpleJobTest {

  @Autowired private JobLauncherTestUtils launcherUtils;

  @Test
  public void 스프링_배치_테스트를_실행할_수_있다() throws Exception {
    // given
    JobParameters jobParameters =
        new JobParametersBuilder().addString("name", "test2").toJobParameters();

    // when
    JobExecution execution = launcherUtils.launchJob(jobParameters);

    // then
    Assertions.assertEquals(execution.getStatus(), BatchStatus.COMPLETED);
    Assertions.assertEquals(execution.getExitStatus(), ExitStatus.COMPLETED);
  }
}
