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
		for (EventDetails evenDetails : allEventDetails) {
			String eventId = evenDetails.getEventId();
			HashMap<String, Boolean> userIds = evenDetails.getUserIds();
			String refGender = null;
			String refUserId = null;
			if (userIds != null && !userIds.isEmpty()) {
				for (String userId : userIds.keySet()) {
					if (isFirst) {
						refUserId = userId;
						User refUser = fbUsersService.get(userId);
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
		
	}
}