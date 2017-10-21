package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * Created by psekar on 10/21/17.
 */
@Service
public class FBEventsService {

    public static String url = "https://graph.facebook.com/v2.10/";

    @Autowired
    ObjectMapper objectMapper;

    public List<FBEvents> getEvents(String userId, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        List<FBEvents> fbEvents = null;
        StringBuffer url = new StringBuffer();
        url.append(userId);
        url = url.append("/events?access_token=");
        url = url.append(accessToken);
        url = url.append("&debug=all&format=json&method=get&pretty=0&suppress_http_code=1");
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url.toString(), List.class, new HashMap<>());
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            fbEvents = (List<FBEvents>)responseEntity.getBody();
        }
        return fbEvents;
    }
    

    @Autowired
    private FBEventsDao fbEventsDao;

    public void save(FBEvents fbEvents) {
    	fbEventsDao.addEvent(fbEvents);
    }

    public User get(String userId) {
        return fbEventsDao.getUsers(userId);
    }

    public EventDetails isEventExists(String eventId){
		return fbEventsDao.isEventExists(eventId);
    	
    }

}
