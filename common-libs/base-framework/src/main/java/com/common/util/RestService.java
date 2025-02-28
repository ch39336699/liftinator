package com.common.util;

import com.common.model.ElevatorRequest;
import com.common.model.ElevatorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
             json = ow.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpEntity<String> entity = new HttpEntity<String>(json, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        ElevatorResponse response2 = new ElevatorResponse();
        return response2;

    }


}
