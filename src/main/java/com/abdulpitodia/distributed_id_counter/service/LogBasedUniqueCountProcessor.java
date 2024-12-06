package com.abdulpitodia.distributed_id_counter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;

@Service
public class LogBasedUniqueCountProcessor implements UniqueCountProcessor{

    Logger logger = LoggerFactory.getLogger(LogBasedUniqueCountProcessor.class);

    @Override
    public void processUniqueRequestCounts(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor) {

        LocalDateTime previousMinute = LocalDateTime.now(ZoneOffset.UTC).minusMinutes(1).withSecond(0).withNano(0);
        String previousMinuteKey = keyExtractor.apply(previousMinute);

        Long uniqueRequestsCount = setOps.size(previousMinuteKey);

        logger.info("Unique requests processed for the minute {} : {}", previousMinute, uniqueRequestsCount);
    }
}
