package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by psekar on 10/21/17.
 */
public class FBEventsParentObj {
	
	@JsonProperty("data")
    List<FBEvents> fbEvents;

    public List<FBEvents> getFbEvents() {
        return fbEvents;
    }

    public void setFbEvents(List<FBEvents> fbEvents) {
        this.fbEvents = fbEvents;
    }
}
