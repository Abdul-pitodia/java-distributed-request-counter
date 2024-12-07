package com.abdulpitodia.distributed_id_counter;

import com.abdulpitodia.distributed_id_counter.service.EndPointHandlerInterface;
import com.abdulpitodia.distributed_id_counter.service.GetBasedEndPointHandlerImpl;
import com.abdulpitodia.distributed_id_counter.service.RequestProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
class DistributedIdCounterApplicationTests {

	@Autowired
	EndPointHandlerInterface countSubmitter;

	@Autowired
	private RedisTemplate<String, Integer> redisTemplate;

	@Autowired
	private SetOperations<String, Integer> setOps;

	@Test
	void contextLoads() {
	}

	@Test
	void testBeanSelection() {
		assertTrue(countSubmitter instanceof GetBasedEndPointHandlerImpl);
	}

	@Test
	public void testBeansAreInjected() {
		assertNotNull(redisTemplate);
		assertNotNull(setOps);
	}

}
