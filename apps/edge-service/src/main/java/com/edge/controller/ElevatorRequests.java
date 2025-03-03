package com.edge.controller;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.edge.services.APIProcessor;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.argument.StructuredArguments.kv;

/*
 * Project: liftinator
 * File: ElevatorRequests.java
 * Author: Chris Harper
 * The ElevatorRequests class is a Spring controller designed to handle HTTP POST requests at the /elevatorAction endpoint.
 * It processes ElevatorRequest objects, delegates the logic to APIProcessor, and logs the request details.
 */
@RestController
@Slf4j
public class ElevatorRequests {

    @Autowired
    APIProcessor pocessor;

    @Operation(summary = "Elevator Actions", description = "Specify occupants waiting for elevators")
    @PostMapping(
            value = "/elevatorAction",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Accepts Elevator Actions.")
    public ElevatorResponse post(@RequestBody ElevatorRequest request) {
        ElevatorResponse response = null;
        try {
            printRequest(request);
            response = pocessor.processRequest(request);
        } catch (Exception ex) {
            log.error("ElevatorResponse.post(), Exception: {}", ExceptionUtils.getStackTrace(ex));
        }
        return response;
    }

    private void printRequest(ElevatorRequest request) {
        JSONObject data = new JSONObject();
        try {
            if ((request != null) && (request.body != null)) {
                data.put("numEntering", request.body.occupantsEntering.size());
                log.info("ElevatorResponse.printRequest(),{}",  kv("BODY", data));
            } else {
                log.warn("ElevatorResponse.printRequest(), Request is NULL");
            }
        } catch (Exception ex) {
            log.error("ElevatorResponse.printRequest(), Exception: {}", ExceptionUtils.getStackTrace(ex));
        }
    }

}
