package org.springframework.samples.mvc.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class CronJob {

	@Autowired
	FBEventsService fbEventsService;

	@Autowired
	private FBUsersService fbUsersService;

	@Autowired
	MessengerBot messengerBot;


	@Scheduled(cron = "* * * 1 * *")
	public void newJSonDataPair() {
		saveUserEvents();
		populateEventMatching();
		HashMap<String, List<String>> eventPaxIds = processEvents();
		fbEventsService.saveEventPaxSortedDetails(eventPaxIds);
	}

	private HashMap<String, List<String>> processEvents() {
		List<EventDetails> allEventDetails = fbEventsService.getAllEventDetails();
		boolean isFirst = true;
		for (EventDetails evenDetails : allEventDetails) {
			String eventId = evenDetails.getEventId();
			HashMap<String, Boolean> userIds = evenDetails.getUserIds();
			String refGender = null;
			String refUserId = null;
			User refUser = null;
			if (userIds != null && !userIds.isEmpty()) {
				for (String userId : userIds.keySet()) {
					if (isFirst) {
						refUserId = userId;
						refUser = fbUsersService.get(userId);
						refGender = refUser.getGender();
						isFirst = false;
					} else {
						//HashMap<String, Boolean> paxIdHashMap = new HashMap<String, Boolean>();
						EvenIdsPaxIdsVO evenIdsPaxIdsVO = new EvenIdsPaxIdsVO();
						User user = fbUsersService.get(userId);
						String Gender = user.getGender();
						if (!refGender.equals(Gender)) { // ready to save
							HashMap<String, Boolean> paxIdHashMap = null;
							paxIdHashMap.put(refUserId, false);
							evenIdsPaxIdsVO.setPaxOne(paxIdHashMap);
							paxIdHashMap.put(userId, false);
							evenIdsPaxIdsVO.setPaxOne(paxIdHashMap);
						}
						evenIdsPaxIdsVO.setEventId(eventId);
						fbEventsService.saveEventPaxSortedDetails(evenIdsPaxIdsVO);
					}
				}
			}
		}
		return eventPaxIds;
	}

	public void populateEventMatching() {
		List<FBEvents> fbEventsList = fbEventsService.getAllFBEvents();
		for (FBEvents fbEvents : fbEventsList) {
			EventDetails eventDetails = fbEventsService.getEventDetails(fbEvents.getId());
			if(eventDetails != null) {
				eventDetails.getUserIds().add(fbEvents.getUserId());
			} else {
				eventDetails = new EventDetails();
				eventDetails.setEventId(fbEvents.getId());
				ArrayList<String> userIds = new ArrayList<String>();
				userIds.add(fbEvents.getUserId());
				eventDetails.setUserIds(userIds);
			}
			fbEventsService.saveEventDetails(eventDetails);
		}
	}


	public void saveUserEvents() {
		List<User> list = fbUsersService.getUsers();
		for (User user : list) {
			List<FBEvents> fbEventsList = fbEventsService.getEvents(user.getUserId(), user.getAccessToken());
			for (FBEvents fbEvents : fbEventsList) {
				fbEvents.setUserId(user.getUserId());
			}
			fbEventsService.save(fbEventsList);
		}
		fbEventsService.saveEventPaxSortedDetails(eventPaxIds);
	}
}