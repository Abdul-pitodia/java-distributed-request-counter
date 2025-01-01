package com.abdulpitodia.distributed_id_counter.controller;


import com.abdulpitodia.distributed_id_counter.service.RequestProcessorService;
import com.abdulpitodia.distributed_id_counter.validators.annotations.ValidEndPoint;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
public class Endpoint {

    Logger logger = LoggerFactory.getLogger(Endpoint.class);

    @Autowired
    private RequestProcessorService requestProcessorService;

    @GetMapping("/api/accept")
    @ResponseBody()
    public ResponseEntity<String> acceptRequest(
            @RequestParam(name = "id") @Positive(message = "The id parameter must be a positive integer") Integer id,
            @RequestParam(name = "endpoint", required = false) @ValidEndPoint String httpEndPoint) {
        try {
            requestProcessorService.acceptRequest(id);
            if (Objects.nonNull(httpEndPoint)) {
                requestProcessorService.submitCount(httpEndPoint);
            }
            logger.info("Successfully accepted request");
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            logger.error("Exception occurred while processing request: {}", e.getMessage());
        }
        return ResponseEntity.internalServerError().body("failed");
    }

}
