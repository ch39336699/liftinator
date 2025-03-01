package com.common.util;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestService {

    private RestTemplate restTemplate = new RestTemplate();
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    String json = null;

    public ElevatorResponse post(String url, ElevatorRequest request) {
        ElevatorResponse response = new ElevatorResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            json = ow.writeValueAsString(request);
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        } catch (Exception ex) {
            log.error("RestService.post(): ---> Exception: {}", ExceptionUtils.getStackTrace(ex));
        }
        return response;

    }


}
