package com.abdulpitodia.distributed_id_counter.config;

import com.abdulpitodia.distributed_id_counter.service.CountSubmitter;
import com.abdulpitodia.distributed_id_counter.service.GetBasedCountSubmitter;
import com.abdulpitodia.distributed_id_counter.service.LogBasedUniqueCountProcessor;
import com.abdulpitodia.distributed_id_counter.service.UniqueCountProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public UniqueCountProcessor uniqueCountProcessor(){
        return new LogBasedUniqueCountProcessor();
    }
    @Bean
    public CountSubmitter countSubmitter(){
        return new GetBasedCountSubmitter();
    }
}
