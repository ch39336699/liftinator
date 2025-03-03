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

@RestController
@Slf4j
public class ElevatorRequests {

    @Autowired
    APIProcessor pocessor;

    @Operation(summary = "Get all users", description = "Returns a list of users")
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
            log.error("ElevatorRequests.post(), Exception: {}", ExceptionUtils.getStackTrace(ex));
        } finally {

        }
        return response;
    }

    private void printRequest(ElevatorRequest request) {
        JSONObject data = new JSONObject();
        try {
            if ((request != null) && (request.body != null)) {
                //data.put("currentFloor", request.body.currentFloor);
                //data.put("direction", request.body.direction);
                data.put("numEntering", request.body.occupantsEntering.size());
                log.info("ElevatorRequests.printRequest(),{}",  kv("BODY", data));
            } else {
                log.warn("ElevatorRequests.printRequest(), Request is NULL");
            }
        } catch (Exception ex) {
            log.error("ElevatorRequests.printRequest(), Exception: {}", ExceptionUtils.getStackTrace(ex));
        }
    }

}
