package org.springframework.samples.mvc.views;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("event/")
public class EventController {

	@Autowired
	private FBEventsService fbEventsService;


	@RequestMapping(value="get", method=RequestMethod.GET)
	public  void get(String userId,String accessToken) {
		 List<FBEvents> fbEvents = fbEventsService.getEvents(userId,accessToken);
		 for (FBEvents fbEvent : fbEvents) {
			 if( fbEvent.getEnd_time()!=null
					 && fbEvent.getName()!=null
					 && fbEvent.getPlace()!=null
					 && fbEvent.getPlace().getLocation()!=null
					 && fbEvent.getPlace().getLocation().getLatitude()!=null
					 && fbEvent.getPlace().getLocation().getLongitude()!=null
					 && fbEvent.getRsvp_status()!= null
					 && fbEvent.getStart_time()!=null
					 && fbEvent.getPlace()!=null){
			 fbEventsService.save(fbEvent);
			 }
			 
			 
			 if(fbEventsService.isEventExists(fbEvent.getId())!=null){
				 EventDetails eventDetails = fbEventsService.isEventExists(fbEvent.getId());
				 List<String> userIds = eventDetails.getUserIds();
				 userIds.add(userId);
			 }else{
				 EventDetails eventDetails = new EventDetails();
				 eventDetails.setEventId(fbEvent.getId());
				 
				 List<String> userIds = new ArrayList<String>();
				 userIds.add(userId);
				 eventDetails.setUserIds(userIds);;
			 
			 }
		 }		 
	}
	
	
	public String pushNotificationAuth(String userId,String yesOrNo){
		return fbEventsService.getUserIdForGivenUser(userId,yesOrNo);
		
	}
	
}
