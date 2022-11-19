package com.yaini.adapter.in.batch;

import com.yaini.adapter.in.batch.item.CustomerItem;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class XMLStaxEventJobConfig {

  public static final String JOB_NAME = "XML_STAX_EVENT_JOB";
  public static final String FILE_PATH = "./";
  public static final String FILE_NAME = "customer.xml";
  public static final int CHUNK_SIZE = 2;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean(JOB_NAME)
  public Job xmlStatEventJob() {

    return jobBuilderFactory.get(JOB_NAME).start(xmlStaxEventStep()).build();
  }

  @Bean
  public Step xmlStaxEventStep() {

    return stepBuilderFactory
        .get("xmlStaxEventStep")
        .<CustomerItem, CustomerItem>chunk(CHUNK_SIZE)
        .reader(xmlFileItemReader())
        .writer(xmlFileItemWriter())
        .build();
  }

  @Bean
  public ItemReader<CustomerItem> xmlFileItemReader() {

    return new StaxEventItemReaderBuilder<CustomerItem>()
        .name("xmlFileItemReader")
        .resource(new ClassPathResource(FILE_PATH + FILE_NAME))
        .addFragmentRootElements("customer")
        .unmarshaller(itemMarshaller())
        .build();
  }

  @Bean
  public XStreamMarshaller itemMarshaller() {
    Map<String, Class<?>> aliases = new HashMap<>();
    aliases.put("customer", CustomerItem.class);
    aliases.put("id", Long.class);
    aliases.put("name", String.class);
    aliases.put("birth", LocalDateTime.class);

    XStreamMarshaller marshaller = new XStreamMarshaller();
    marshaller.setAliases(aliases);

    return marshaller;
  }

  @Bean
  public ItemWriter<CustomerItem> xmlFileItemWriter() {

    return new StaxEventItemWriterBuilder<CustomerItem>()
        .name("xmlFileItemWriter")
        .resource(new FileSystemResource(FILE_PATH + "new_" + FILE_NAME))
        .rootTagName("customer")
        .marshaller(itemMarshaller())
        .overwriteOutput(true)
        .build();
  }
}
