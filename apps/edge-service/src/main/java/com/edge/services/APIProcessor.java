package com.edge.services;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.common.util.RestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class APIProcessor {

    RestService restService = new RestService();

    public ElevatorResponse processRequest(ElevatorRequest request) {
        ElevatorResponse response = null;
        try {
            response = restService.post("http://capacity-coordinator-service:8082/elevatorAction", request);
        } catch (Exception ex) {
            log.error("RestService.post(): Exception: {}", ExceptionUtils.getStackTrace(ex));
        } finally {

        }
        return response;
    }


}
