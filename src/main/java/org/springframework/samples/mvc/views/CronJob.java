package org.springframework.samples.mvc.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
		HashMap<String, List<String>> eventPaxIds = new HashMap<>();
		for (EventDetails evenDetails : allEventDetails) {
			String eventId = evenDetails.getEventId();
			List<String> userIds = evenDetails.getUserIds();
			String refGender = null;
			String refUserId = null;
			User refUser = null;
			if (userIds != null && !userIds.isEmpty()) {
				for (String userId : userIds) {
					if (isFirst) {
						refUserId = userId;
						refUser = fbUsersService.get(userId);
						refGender = refUser.getGender();
						isFirst = false;
					} else {
						List<String> paxIds = new ArrayList<String>();
						User user = fbUsersService.get(userId);
						String Gender = user.getGender();
						if (!refGender.equals(Gender)) { // ready to save
							paxIds.add(refUserId);
							paxIds.add(userId);
						}
						eventPaxIds.put(eventId, paxIds);
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
	}
}