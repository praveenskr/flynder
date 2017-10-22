package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by psekar on 10/21/17.
 */
@Repository
public class FBEventsDao {

    @Autowired
    private MongoOperations mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public User getUsers(String userId) {
        Query searchUserQuery = new Query(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(searchUserQuery, User.class);
        try {
            objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return user;
    }

   
    public void addEvent(FBEvents fbEvents) {

        mongoTemplate.save(fbEvents);
    }

    public void saveEventPaxSortedDetails(EvenIdsPaxIdsVO evenIdsPaxIdsVO) {

        mongoTemplate.save(evenIdsPaxIdsVO);
    }



    public void addEvent(List<FBEvents> fbEvents) {

        mongoTemplate.save(fbEvents);
    }

    public void saveEventPaxSortedDetails(List<EvenIdsPaxIdsVO> eventPaxDetails) {

        mongoTemplate.save(eventPaxDetails);
    }


    public EventDetails isEventExists(String eventId){

        Query searchUserQuery = new Query(Criteria.where("eventId").is(eventId));
        EventDetails eventDetails = mongoTemplate.findOne(searchUserQuery, EventDetails.class);
        return eventDetails;    
    }
    
    public String getUserIdForGivenUser(String userId,String yesOrNo){

        Query searchUserQuery = new Query(Criteria.where("paxOne").is(userId));
        UserDetails userDetails = null;
        EvenIdsPaxIdsVO evenIdsPaxIdsVO = mongoTemplate.findOne(searchUserQuery, EvenIdsPaxIdsVO.class);
        String matchedUserId =null;
        if(evenIdsPaxIdsVO!=null){
        	HashMap<String,Boolean> eventPaxIdMap = evenIdsPaxIdsVO.getPaxOne();
        	Set<String> userIdsSet = eventPaxIdMap.keySet();
        	for (String userIdString : userIdsSet) {
        		if(userIdString.equalsIgnoreCase(userId)
        				&& "YES".equalsIgnoreCase(yesOrNo)){
        				eventPaxIdMap.put(userIdString, true);
        		        saveEventPaxSortedDetails(evenIdsPaxIdsVO);
        		}
        			if(!userIdString.equalsIgnoreCase(userId)){
    					if(eventPaxIdMap.get(userIdString)){
    						matchedUserId = userIdString;
    					}
    				}
			}
        }

        if(matchedUserId!=null){
        	userDetails = getUserEventDetails(userId,matchedUserId);
    				}

			}

        }

        return matchedUserId;
    
    }
    /**
     *
     * @param userId
     * @param matchedUserId
     * @return
     */
    public UserDetails getUserEventDetails(String userId,String matchedUserId) {

        Query searchQuery = new Query(Criteria.where("eventId").is(userId));
        FBEvents fbEvents = mongoTemplate.findOne(searchQuery, FBEvents.class);

        UserDetails userDetails = new UserDetails();
        List<String> userIds = new ArrayList<String>();
        userDetails.setUserIds(userIds);
        LocationOfUser locationOfUser = new LocationOfUser();
        locationOfUser.setLatitude(fbEvents.getPlace().getLocation().getLatitude());
        locationOfUser.setLongitude(fbEvents.getPlace().getLocation().getLongitude());
        userDetails.setStartDate(fbEvents.getStart_time());
        userDetails.setLocationOfUser(locationOfUser);
        return userDetails;
    }
}
