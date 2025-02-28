package com.capacity.controller;

import com.capacity.services.Corridnator;
import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ElevatorRequests {

    @Autowired
    Corridnator corridnator;

    private final String endpoint = "routeRequest";

   // @Value("${api." + endpoint + ".ruleset}")
   // private String ruleset;

    String status = "Status:";

    @PostMapping(
            value = "/awbRerouteRequest",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Accepts CDV Reroute Request, processes it and provides new Reroute.")
    public ElevatorResponse postBody(@RequestBody ElevatorRequest rerouteRequest) {

        ElevatorResponse response = null;
        try {
            printRequest(rerouteRequest);
            response = corridnator.processRequest();
        } catch (Exception ex) {
            log.error("ElevatorRequests.postBody(), Exception: {}", ExceptionUtils.getStackTrace(ex));
        } finally {

        }
        //Send location in response
        return response;
    }

    private void printRequest(ElevatorRequest request) {
        JSONObject reroute = new JSONObject();
        try {
//            if ((request != null) && (request.getBody() != null) && (request.getBody().getReroute() != null)) {
//                reroute.put("eventUUID", request.getBody().getReroute().getEventUUID());
//                reroute.put("eventCreateDateTime", request.getBody().getReroute().getEventCreateDateTime());
//                reroute.put("sourceSystem", request.getBody().getReroute().getSourceSystem());
//                log.info("RerouteRequests  HEADER: {} ", request.getHdr().toString(), kv("BODY", reroute));
//            } else {
//                log.warn("RerouteRequests.printRequest(), Manual Reroute request is NULL");
//            }
        } catch (Exception ex) {
            log.error("ElevatorRequests.printRequest(), Exception: {}", ExceptionUtils.getStackTrace(ex));
        }
    }

}
