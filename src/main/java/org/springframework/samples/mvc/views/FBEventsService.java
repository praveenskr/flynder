package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by psekar on 10/21/17.
 */
@Service
public class FBEventsService {

    public static String url = "https://graph.facebook.com/v2.10/1526298254123626/events?access_token=EAAByTJgYNysBADeFUbh6xccEjPCZBkJZCI79oAFHWclfPO9k4D3LJ2d210GeYwR5TAabE18XqWloTQZBkQUwAftgh0XY8n3jR96Tf0GAaRIypuAIm5Ijf8XU1AwdwiknQTRn7JM9B11gGAbKBzn3EulUKPGO9qiqEJQ4iH2o99sPCMZBEKelZChqvVYCGgNA64I74KgJjfQZDZD&debug=all&format=json&method=get&pretty=0&suppress_http_code=1";

    @Autowired
    ObjectMapper objectMapper;

    public void getEvents() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FBEvents> responseEntity = restTemplate.getForEntity(url, FBEvents.class, new HashMap<>());
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            FBEvents fbEvents = responseEntity.getBody();
            try {
                objectMapper.writeValueAsString(fbEvents);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

}
