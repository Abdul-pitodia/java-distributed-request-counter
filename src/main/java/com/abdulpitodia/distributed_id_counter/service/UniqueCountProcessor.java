package com.abdulpitodia.distributed_id_counter.service;

import org.springframework.data.redis.core.SetOperations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;

public interface UniqueCountProcessor {

    public void processUniqueRequestCounts(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor);
}
