package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EventDetails")
public class EventDetails {

	@JsonProperty("eventId")
    private String eventId;

	@JsonProperty("userIds")
	
	List<String> userIds;

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the userIds
	 */
	public List<String> getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	
}