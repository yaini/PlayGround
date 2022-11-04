package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.validator.SimpleJobParameterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class SimpleJobConfig {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job batchJob(){

		return jobBuilderFactory.get("batchJob")
				.start(startStep())
				.next(nextStep())
				.validator(new SimpleJobParameterValidator())
				// .validator(new DefaultJobParametersValidator(new String[]{"name"}, new String[]{"type"}))
				.build();
	}

	@Bean
	public Step startStep(){

		return stepBuilderFactory.get("startStep")
				.tasklet( ((contribution, chunkContext) -> RepeatStatus.FINISHED))
				.build();
	}

	@Bean
	public Step nextStep(){

		return stepBuilderFactory.get("nextStep")
				.tasklet( ((contribution, chunkContext) -> RepeatStatus.FINISHED))
				.build();
	}

}
