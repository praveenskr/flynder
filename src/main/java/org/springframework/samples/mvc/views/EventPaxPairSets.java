package org.springframework.samples.mvc.views;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "EventPaxPairSets")
public class EventPaxPairSets {
	
		@Id
	    private String id;

		@JsonProperty("userId")
		String userId;
		
		@JsonProperty("PaxReadyToBookStatus")
		HashMap PaxReadyToBookStatus;

		public HashMap getPaxReadyToBookStatus() {
			return PaxReadyToBookStatus;
		}

		public void setPaxReadyToBookStatus(HashMap paxReadyToBookStatus) {
			PaxReadyToBookStatus = paxReadyToBookStatus;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
	}

}
