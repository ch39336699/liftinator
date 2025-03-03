package com.capacity.controller;

import com.capacity.services.Corridnator;
import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/*
 * Project: liftinator
 * File: ElevatorRequests.java
 * Author: Chris Harper
 * The ElevatorRequests class is a Spring-based REST controller that exposes an endpoint to accept
 * and process elevator actions. This class is responsible for receiving ElevatorRequest objects,
 * delegating the request processing to the Corridnator class, and returning an ElevatorResponse.
 */
@RestController
@Slf4j
public class ElevatorRequests {

    @Autowired
    Corridnator corridnator;

    @PostMapping(
            value = "/elevatorAction",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Accepts Elevator Actions.")
    public ElevatorResponse post(@RequestBody ElevatorRequest request) {
        ElevatorResponse response = null;
        try {
            response = corridnator.processRequest(request);

        } catch (Exception ex) {
            log.error("ElevatorResponse.post(), Exception: {}", ExceptionUtils.getStackTrace(ex));
        }
        return response;
    }

}
