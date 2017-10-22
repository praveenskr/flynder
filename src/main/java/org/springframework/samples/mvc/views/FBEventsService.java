package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
        ResponseEntity<FBEventsParentObjClone> responseEntity = restTemplate.getForEntity(url.toString(), FBEventsParentObjClone.class, new HashMap<>());
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            fbEvents = new ArrayList<>();
            List<FBEventsClone> fbEventsClones = responseEntity.getBody().getFbEvents();
            for (FBEventsClone fbEventsClone1 : fbEventsClones) {
                FBEvents fbEvents1 = new FBEvents();
                fbEvents1.setUserId(fbEventsClone1.getUserId());
                fbEvents1.setDescription(fbEventsClone1.getDescription());
                fbEvents1.setEnd_time(fbEventsClone1.getEnd_time());
                fbEvents1.setName(fbEventsClone1.getName());
                fbEvents1.setPlace(fbEventsClone1.getPlace());
                fbEvents1.setRsvp_status(fbEventsClone1.getRsvp_status());
                fbEvents1.setStart_time(fbEventsClone1.getStart_time());
                fbEvents1.setEventId(fbEventsClone1.getId());
                fbEvents.add(fbEvents1);
            }
        }
        return fbEvents;
    }
    

    @Autowired
    private FBEventsDao fbEventsDao;

    public void save(FBEvents fbEvents) {
    	fbEventsDao.addEvent(fbEvents);
    }

    public FBEvents getFbEvent(String eventId) {
        Query searchUserQuery = new Query(Criteria.where("eventId").is(eventId));
        return mongoTemplate.findOne(searchUserQuery, FBEvents.class);
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

	public void saveEventPaxSortedDetails(List<EvenIdsPaxIdsVO> eventPaxDetails) {
    	fbEventsDao.saveEventPaxSortedDetails(eventPaxDetails);
    }

    public void saveEventPaxSortedDetails(EvenIdsPaxIdsVO eventPaxDetails) {
        fbEventsDao.saveEventPaxSortedDetails(eventPaxDetails);
    }

    public EvenIdsPaxIdsVO getEvenIdsPaxIdsVO(String id) {
        Query searchUserQuery = new Query(Criteria.where("eventId").is(id));
        return mongoTemplate.findOne(searchUserQuery, EvenIdsPaxIdsVO.class);
    }

    public void updateEvenIdsPaxIdsVO(EvenIdsPaxIdsVO evenIdsPaxIdsVO) {
        Query query = new Query();
        query.addCriteria(Criteria.where("eventId").is(evenIdsPaxIdsVO.getEventId()));
        Update update = new Update();
        update.set("sentMessage",  "notified");

        WriteResult wr = mongoTemplate.updateMulti(
                new Query(Criteria.where("eventId").is(evenIdsPaxIdsVO.getEventId())),new Update().set("sentMessage", "notified"),EvenIdsPaxIdsVO.class);
    }

    public void saveEventDetails(EventDetails eventDetails) {
        mongoTemplate.save(eventDetails);
    }

    public EventDetails isEventExists(String eventId){
		return fbEventsDao.isEventExists(eventId);
    	
    }

    public UserDetails getUserIdForGivenUser(String userId,String yesOrNo){
    	return fbEventsDao.getUserIdForGivenUser(userId,yesOrNo);
    }
    public List<EvenIdsPaxIdsVO> getAllEvenIdsPaxIdsVO() {
        return mongoTemplate.findAll(EvenIdsPaxIdsVO.class);
    }

}
