package com.edge.services;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.common.util.RestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
 * Project: liftinator
 * File: APIProcessor.java
 * Author: Chris Harper
 * The APIProcessor class is responsible for processing an ElevatorRequest by determining the appropriate URL
 * (depending on whether the application is running inside a Docker container) and sending the request
 * to that URL using the RestService
 */
@Slf4j
@Service
public class APIProcessor {

    RestService restService = new RestService();

    public ElevatorResponse processRequest(ElevatorRequest request) {
        ElevatorResponse response = null;
        String url = "http://localhost:8084/elevatorAction";
        try {
            Path dockerEnvPath = Paths.get("/.dockerenv");
            boolean dockerCheck = Files.exists(dockerEnvPath);
            log.info("Docker check: {}", dockerCheck);
            if (dockerCheck) {
                url = "http://capacity-coordinator-service:8084/elevatorAction";
            }
            response = restService.post(url, request);
        } catch (Exception ex) {
            log.error("APIProcessor.processRequest(): Exception: {}", ExceptionUtils.getStackTrace(ex));
        }
        return response;
    }


}
