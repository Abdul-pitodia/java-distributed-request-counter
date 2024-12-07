package com.abdulpitodia.distributed_id_counter.service;

import org.springframework.data.redis.core.SetOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.function.Function;

public interface UniqueCountProcessor {

    public void processUniqueRequestCounts(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor);

    default Long fetchCurrentUniqueRequestCount(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor){
        String key = keyExtractor.apply(LocalDateTime.now(ZoneOffset.UTC).minusMinutes(1).withSecond(0).withNano(0));
        return setOps.size(key);
    }
}
