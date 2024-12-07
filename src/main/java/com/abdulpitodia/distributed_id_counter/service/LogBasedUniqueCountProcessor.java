package com.abdulpitodia.distributed_id_counter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;

public class LogBasedUniqueCountProcessor implements UniqueCountProcessor{

    Logger logger = LoggerFactory.getLogger(LogBasedUniqueCountProcessor.class);

    @Override
    public void processUniqueRequestCounts(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor) {

        Long uniqueRequestsCount = fetchCurrentUniqueRequestCount(setOps, keyExtractor);

        logger.info("Unique requests processed for the last minute : {}", uniqueRequestsCount);
    }
}
