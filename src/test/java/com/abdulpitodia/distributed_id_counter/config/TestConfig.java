package com.abdulpitodia.distributed_id_counter.config;

import com.abdulpitodia.distributed_id_counter.service.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class TestConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return mock(RedisConnectionFactory.class);
    }

    @Bean
    public RedisTemplate<String, Integer> redisTemplate() {
        RedisTemplate<String, Integer> mockRedisTemplate = mock(RedisTemplate.class);
        SetOperations<String, Integer> setOperations = mock(SetOperations.class);
        when(mockRedisTemplate.opsForSet()).thenReturn(setOperations);
        return mockRedisTemplate;
    }

    @Bean
    public SetOperations<String, Integer> setOperations(RedisTemplate<String, Integer> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean(name = "testlogBean")
    @ConditionalOnProperty(name = {"count.processor.type"}, havingValue = "log", matchIfMissing = true)
    public UniqueCountProcessor getTestLogBean(){
        return new LogBasedUniqueCountProcessor();
    }

    @Bean(name = "testGetSubmitter")
    @Primary
    @ConditionalOnProperty(name = {"count.submitter.type"}, havingValue = "GET", matchIfMissing = true)
    public EndPointHandlerInterface getTestGetCountSubmitter(){
        return new GetBasedEndPointHandlerImpl();
    }

    @Bean(name = "testPostSubmitter")
    @ConditionalOnProperty(name = {"count.submitter.type"}, havingValue = "POST")
    public EndPointHandlerInterface getTestPostCountSubmitter(){
        return new PostBasedEndPointHandlerImpl();
    }
    @Bean
    public RestTemplate restTemplate() {
        return mock(RestTemplate.class);
    }
}
