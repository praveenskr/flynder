package org.springframework.samples.mvc.views;

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

    public static String base_url = "https://graph.facebook.com/v2.10/";

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MongoOperations mongoTemplate;


    public List<FBEvents> getEvents(String userId, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        List<FBEvents> fbEvents = null;
        StringBuffer url = new StringBuffer(base_url);
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

    public void save(List<FBEvents> fbEventsList) {
        fbEventsDao.addEvent(fbEventsList);
    }

    public User get(String userId) {
        return fbEventsDao.getUsers(userId);
    }

	// Added by Jithin R Shenoy
	public List<EventDetails> getAllEventDetails() {
		return mongoTemplate.findAll(EventDetails.class);
	}

    // Added by Jithin R Shenoy
    public List<FBEvents> getAllFBEvents() {
        return mongoTemplate.findAll(FBEvents.class);
    }

    public EventDetails getEventDetails(String eventId) {
        Query searchUserQuery = new Query(Criteria.where("eventId").is(eventId));
        return mongoTemplate.findOne(searchUserQuery, EventDetails.class);
    }

	public void saveEventPaxSortedDetails(HashMap eventPaxDetails) {
    	fbEventsDao.saveEventPaxSortedDetails(eventPaxDetails);
    }

    public void saveEventDetails(EventDetails eventDetails) {
        mongoTemplate.save(eventDetails);
    }

    public EventDetails isEventExists(String eventId){
		return fbEventsDao.isEventExists(eventId);

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
