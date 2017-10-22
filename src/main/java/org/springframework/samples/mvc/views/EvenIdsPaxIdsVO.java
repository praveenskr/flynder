package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection = "EvenIdsPaxIdsVO")
public class EvenIdsPaxIdsVO {

	@Id
    private String id;

	@JsonProperty("eventId")
	String eventId;

	@JsonProperty("sentMessage")
	String sentMessage;

	HashMap<String, Boolean> paxOne = new HashMap<String, Boolean>();

	public String getSentMessage() {
		return sentMessage;
	}

	public void setSentMessage(String sentMessage) {
		this.sentMessage = sentMessage;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public HashMap<String, Boolean> getPaxOne() {
		return paxOne;
	}

	public void setPaxOne(HashMap<String, Boolean> paxOne) {
		this.paxOne = paxOne;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}