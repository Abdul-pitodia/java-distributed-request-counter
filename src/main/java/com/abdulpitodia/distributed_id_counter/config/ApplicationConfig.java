package com.abdulpitodia.distributed_id_counter.config;

import com.abdulpitodia.distributed_id_counter.service.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@Configuration
@Profile("!test")
public class ApplicationConfig {

    @Bean
    @ConditionalOnProperty(name = {"count.processor.type"}, havingValue = "log", matchIfMissing = true)
    public UniqueCountProcessor uniqueCountProcessorLogBased(){
        return new LogBasedUniqueCountProcessor();
    }

    @Bean
    @ConditionalOnProperty(name = {"count.processor.type"}, havingValue = "kafka")
    public UniqueCountProcessor uniqueCountProcessorKafkaBased(){
        return new KafkaBasedUniqueCountProcessor();
    }

    @Bean(name = "getSubmitter")
    @ConditionalOnProperty(name = {"count.submitter.type"}, havingValue = "GET", matchIfMissing = true)
    public EndPointHandlerInterface getCountSubmitter(){
        return new GetBasedEndPointHandlerImpl();
    }

    @Bean(name = "postSubmitter")
    @ConditionalOnProperty(name = {"count.submitter.type"}, havingValue = "POST")
    public EndPointHandlerInterface postCountSubmitter(){
        return new PostBasedEndPointHandlerImpl();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
