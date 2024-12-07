package com.abdulpitodia.distributed_id_counter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.function.Function;

public class PostBasedEndPointHandlerImpl implements EndPointHandlerInterface {

    @Autowired
    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(PostBasedEndPointHandlerImpl.class);

    @Override
    public void submitCountToEndPoint(SetOperations<String, Integer> setOps, Function<LocalDateTime, String> keyExtractor, String endPoint) {
        try {
            Long count = fetchCurrentUniqueRequestCount(setOps, keyExtractor);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            String requestBody = "{\"count\": " + count + "}";

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            String modifiedEndpoint = UriComponentsBuilder.fromUriString(endPoint)
                    .toUriString();

            ResponseEntity<String> responseEntity = restTemplate.exchange(modifiedEndpoint, HttpMethod.POST, requestEntity, String.class);

            logger.info("[POST] Response code: {}", responseEntity.getStatusCode());
            logger.info("[POST] Response Body: {}", responseEntity.getBody());
        } catch (RestClientException e){
            logger.error("[POST] Request failed with exception {}", e.getMessage());
            throw e;
        }
    }
}
