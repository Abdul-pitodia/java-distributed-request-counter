package com.abdulpitodia.distributed_id_counter.service;

import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.function.Function;

@FunctionalInterface
public interface CountSubmitter {
    public void submitCountToEndPoint(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor, String endPoint);

    default URL getValidUrl(String endPoint) {
        try {
            return new URL(endPoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid endPoint URL", e);
        }
    }
}
