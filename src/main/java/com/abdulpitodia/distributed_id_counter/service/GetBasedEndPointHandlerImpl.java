package com.abdulpitodia.distributed_id_counter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.function.Function;

public class GetBasedEndPointHandlerImpl implements EndPointHandlerInterface {

    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(GetBasedEndPointHandlerImpl.class);


    @Override
    public void submitCountToEndPoint(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor, String endPoint) {

        try {
            Long count = fetchCurrentUniqueRequestCount(setOps, keyExtractor);

            String modifiedEndpoint = UriComponentsBuilder.fromUriString(endPoint)
                    .queryParam("count", count)
                    .toUriString();

            ResponseEntity<String> responseEntity = restTemplate.exchange(modifiedEndpoint, HttpMethod.GET, HttpEntity.EMPTY, String.class);

            logger.info("[GET] Response code: {}", responseEntity.getStatusCode());
            logger.info("[GET] Response body: {}", responseEntity.getBody());

        } catch (RestClientException e){
            logger.error("[GET] Request failed with exception {}", e.getMessage());
            throw e;
        }

    }
}
