package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by psekar on 10/21/17.
 */
public class FBEvents {
	
	@JsonProperty("id")
    private String id;
	@JsonProperty("rsvp_status")
    private String rsvp_status;
	@JsonProperty("end_time")
    private String end_time;
	@JsonProperty("description")
    private String description;
	@JsonProperty("name")
    private String name;
	@JsonProperty("start_time")
    private String start_time;
	@JsonProperty("place")
    private Place place;
	@JsonProperty("userId")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getRsvp_status ()
    {
        return rsvp_status;
    }

    public void setRsvp_status (String rsvp_status)
    {
        this.rsvp_status = rsvp_status;
    }

    public String getEnd_time ()
    {
        return end_time;
    }

    public void setEnd_time (String end_time)
    {
        this.end_time = end_time;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getStart_time ()
    {
        return start_time;
    }

    public void setStart_time (String start_time)
    {
        this.start_time = start_time;
    }

    public Place getPlace ()
    {
        return place;
    }

    public void setPlace (Place place)
    {
        this.place = place;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", rsvp_status = "+rsvp_status+", end_time = "+end_time+", description = "+description+", name = "+name+", start_time = "+start_time+", place = "+place+"]";
    }
}
