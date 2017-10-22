package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EventDetails")
public class EventDetails {

	@JsonProperty("eventId")
    private String eventId;

	@JsonProperty("userIds")
	
	HashMap<String, Boolean> userIds;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public HashMap<String, Boolean> getUserIds() {
		return userIds;
	}

	public void setUserIds(HashMap<String, Boolean> userIds) {
		this.userIds = userIds;
	}


}