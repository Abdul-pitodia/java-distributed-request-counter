package com.abdulpitodia.distributed_id_counter.service;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@EnableScheduling
public class RequestProcessorService {
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;
    @Autowired
    private UniqueCountProcessor uniqueCountProcessor;
    @Autowired
    private EndPointHandlerInterface countSubmitter;
    @Resource(name="redisTemplate")
    private SetOperations<String, Integer> setOps;
    private static final String KEY_PREFIX = "req";

    Logger logger = LoggerFactory.getLogger(RequestProcessorService.class);
    private final Function<LocalDateTime, String> getKeyForTime = (time) -> String.format("%s:%02d:%02d", KEY_PREFIX, time.getHour(), time.getMinute());

    public void acceptRequest(Integer id){
        LocalDateTime currentTime = LocalDateTime.now(ZoneOffset.UTC);

        String minuteKey = getKeyForTime.apply(currentTime);

        setOps.add(minuteKey, id);
        redisTemplate.expire(minuteKey, 2, TimeUnit.MINUTES);
    }

    @Scheduled(cron = "0 * * * * *", zone = "UTC")
    public void processCounts(){
        uniqueCountProcessor.processUniqueRequestCounts(setOps, getKeyForTime);
    }

    public void submitCount(String endPoint){
        countSubmitter.submitCountToEndPoint(setOps, getKeyForTime, endPoint);
    }


}
