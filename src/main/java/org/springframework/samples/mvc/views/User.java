package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

	@Id
    private String id;

	@JsonProperty("userId")
	String userId;

	@JsonProperty("locationNumber")
	String locationId;

	@JsonProperty("number")
	String number;

	@JsonProperty("accessToken")
	String accessToken;

	@JsonProperty("gender")
	String gender;

	@JsonProperty("latitude")
	String latitude;

	@JsonProperty("longitude")
	String longitude;

	@JsonProperty("messengerUserId")
	String messengerUserId;

	@JsonProperty("name")
	String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessengerUserId() {
		return messengerUserId;
	}

	public void setMessengerUserId(String messengerUserId) {
		this.messengerUserId = messengerUserId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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