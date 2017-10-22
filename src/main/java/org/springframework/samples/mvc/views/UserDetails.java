package org.springframework.samples.mvc.views;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by psekar on 10/21/17.
 */
public class UserDetails {
	
	private List<String> userIds;
	
	private LocationOfUser locationOfUser;
	
	private String startDate;

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

	/**
	 * @return the locationOfUser
	 */
	public LocationOfUser getLocationOfUser() {
		return locationOfUser;
	}

	/**
	 * @param locationOfUser the locationOfUser to set
	 */
	public void setLocationOfUser(LocationOfUser locationOfUser) {
		this.locationOfUser = locationOfUser;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param string the startDate to set
	 */
	public void setStartDate(String string) {
		this.startDate = startDate;
	}
	

   }
