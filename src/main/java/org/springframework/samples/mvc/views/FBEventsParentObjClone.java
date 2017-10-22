package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by psekar on 10/21/17.
 */
public class FBEventsParentObjClone {
	
	@JsonProperty("data")
    List<FBEventsClone> fbEvents;

    public List<FBEventsClone> getFbEvents() {
        return fbEvents;
    }

    public void setFbEvents(List<FBEventsClone> fbEvents) {
        this.fbEvents = fbEvents;
    }
}
