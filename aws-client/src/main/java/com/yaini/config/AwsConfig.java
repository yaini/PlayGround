package com.yaini.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

  public final String AWS_REGION = Regions.AP_NORTHEAST_2.getName();
  public final String AWS_ENDPOINT = "http://127.0.0.1:4566";

  public final String LOCAL_STACK_ACCESS_KEY = "foobar";
  public final String LOCAL_STACK_SECRET_KEY = "foobar";

  @Bean
  public AmazonS3 amazonS3() {

    AwsClientBuilder.EndpointConfiguration endpoint =
        new AwsClientBuilder.EndpointConfiguration(AWS_ENDPOINT, AWS_REGION);

    BasicAWSCredentials credentials =
        new BasicAWSCredentials(LOCAL_STACK_ACCESS_KEY, LOCAL_STACK_SECRET_KEY);

    return AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(endpoint)
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .build();
  }
}
