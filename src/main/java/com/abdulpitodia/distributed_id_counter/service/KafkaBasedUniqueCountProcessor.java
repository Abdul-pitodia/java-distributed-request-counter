package com.abdulpitodia.distributed_id_counter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.function.Function;

public class KafkaBasedUniqueCountProcessor implements UniqueCountProcessor{

    Logger logger = LoggerFactory.getLogger(KafkaBasedUniqueCountProcessor.class);

    private static final String TOPIC = "unique-req-count";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void processUniqueRequestCounts(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor) {
        Long uniqueRequestsCount = fetchCurrentUniqueRequestCount(setOps, keyExtractor);
        String message = "\"Unique requests processed for the last minute :" + uniqueRequestsCount;
        kafkaTemplate.send(TOPIC, message);
        logger.info("Sent message ' {} ' to kafka successfully", message);
    }
}
