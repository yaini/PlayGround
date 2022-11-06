package com.yaini.adapter.in.batch.validator;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class SimpleJobParameterValidator implements JobParametersValidator {

  @Override
  public void validate(final JobParameters parameters) throws JobParametersInvalidException {

    if (parameters == null) {
      throw new JobParametersInvalidException("jobParameters is null");
    }
  }
}
