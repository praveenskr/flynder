package org.springframework.samples.mvc.views;

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

	FBEventsService fbEventsService;

	@Autowired
	private FBUsersService fbUsersService;

	@Scheduled(cron = "* * * 1 * *")
	public void newJSonDataPair() {

		List<EventDetails> allEventDetails = fbEventsService.getAllEventDetails();
		boolean isFirst = true;
		HashMap<String, List<String>> eventPaxIds = new HashMap<>();
		for (EventDetails evenDetails : allEventDetails) {
			String eventId = evenDetails.getEventId();
			List<String> userIds = evenDetails.getUserIds();
			String refGender = null;
			String refUserId = null;
			if (userIds != null && !userIds.isEmpty()) {
				for (String userId : userIds) {
					if (isFirst) {
						refUserId = userId;
						User refUser = fbUsersService.get(userId);
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
		fbEventsService.saveEventPaxSortedDetails(eventPaxIds);
	}
}