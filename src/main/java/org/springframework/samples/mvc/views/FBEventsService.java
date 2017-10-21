package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public static String url = "https://graph.facebook.com/v2.10/1526298254123626/events?access_token=EAAByTJgYNysBADeFUbh6xccEjPCZBkJZCI79oAFHWclfPO9k4D3LJ2d210GeYwR5TAabE18XqWloTQZBkQUwAftgh0XY8n3jR96Tf0GAaRIypuAIm5Ijf8XU1AwdwiknQTRn7JM9B11gGAbKBzn3EulUKPGO9qiqEJQ4iH2o99sPCMZBEKelZChqvVYCGgNA64I74KgJjfQZDZD&debug=all&format=json&method=get&pretty=0&suppress_http_code=1";

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MongoOperations mongoTemplate;
    

    public List<FBEvents> getEvents() {
        RestTemplate restTemplate = new RestTemplate();
        List<FBEvents> fbEvents = null;
        ResponseEntity<List> responseEntity = restTemplate.getForEntity(url, List.class, new HashMap<>());
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
    
	// Added by Jithin R Shenoy
	public List getAllEventDetails() {
		List<EventDetails> eventdetails =  mongoTemplate.findAll(EventDetails.class);
		try {
			objectMapper.writeValueAsString(eventdetails);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return eventdetails;
	}
	
	public void saveEventPaxSortedDetails(HashMap eventPaxDetails) {
    	fbEventsDao.saveEventPaxSortedDetails(eventPaxDetails);
    }
    
}
