package org.springframework.samples.mvc.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Service
public class CronJob {

    @Autowired
    FBEventsService fbEventsService;

    @Autowired
    private FBUsersService fbUsersService;

    @Autowired
    MessengerBot messengerBot;

    private static String messageText = "user_name also going to the event_name event. Would you like to travel with this person?. If so, please say 'Yes'";

    @Scheduled(fixedRate = 5000000)
    public void newJSonDataPair() {
        saveUserEvents();
        populateEventMatching();
        List<EvenIdsPaxIdsVO> eventPaxIds = processEvents();
        List<EvenIdsPaxIdsVO> evenIdsPaxIdsVOS = fbEventsService.getAllEvenIdsPaxIdsVO();
        for (EvenIdsPaxIdsVO evenIdsPaxIdsVO : evenIdsPaxIdsVOS) {
            if(evenIdsPaxIdsVO.getSentMessage() == null || !evenIdsPaxIdsVO.getSentMessage().equals("notified")) {
                Set<String> keySet = evenIdsPaxIdsVO.getPaxOne().keySet();
                for (String userId : keySet) {
                    User user = fbUsersService.get(userId);
                    FBEvents fbEvents = fbEventsService.getFbEvent(evenIdsPaxIdsVO.getEventId());
                    String number = user.getNumber();
                    if(user.getName().toLowerCase().contains("praveen")) {
                        number = "+91 8943396673";
                    } else {
                        number = "+91 9746847707";
                    }
                    messageText = messageText.replaceAll("user_name", user.getName());
                    messageText = messageText.replaceAll("event_name", fbEvents.getName());
                    boolean initiateFirstMessageToUser = messengerBot.initiateFirstMessageToUser(number, messageText);
                    if(initiateFirstMessageToUser) {
                        fbEventsService.updateEvenIdsPaxIdsVO(evenIdsPaxIdsVO);
                    }
                }
            }
        }
    }

    private List<EvenIdsPaxIdsVO> processEvents() {
        List<EventDetails> allEventDetails = fbEventsService.getAllEventDetails();
        List<EvenIdsPaxIdsVO> evenIdsPaxIdsVOS = new ArrayList<>();
        for (EventDetails evenDetails : allEventDetails) {
            boolean isFirst = true;
            String eventId = evenDetails.getEventId();
            HashMap<String, Boolean> userIds = evenDetails.getUserIds();
            String refGender = null;
            String refUserId = null;
            User refUser = null;
            EvenIdsPaxIdsVO evenIdsPaxIdsVO = null;
            if (userIds != null && !userIds.isEmpty()) {
                for (String userId : userIds.keySet()) {
                    if (isFirst) {
                        refUserId = userId;
                        refUser = fbUsersService.get(userId);
                        refGender = refUser.getGender();
                        isFirst = false;
                    } else {
                        //HashMap<String, Boolean> paxIdHashMap = new HashMap<String, Boolean>();
                        evenIdsPaxIdsVO = new EvenIdsPaxIdsVO();
                        User user = fbUsersService.get(userId);
                        String gender = user.getGender();
                        if (!refGender.equals(gender)) { // ready to save
                            HashMap<String, Boolean> paxIdHashMap = new HashMap<>();
                            paxIdHashMap.put(refUserId, false);
                            evenIdsPaxIdsVO.setPaxOne(paxIdHashMap);
                            paxIdHashMap.put(userId, false);
                            evenIdsPaxIdsVO.setPaxOne(paxIdHashMap);
                        }
                        evenIdsPaxIdsVO.setEventId(eventId);
                        evenIdsPaxIdsVO.setSentMessage("notnotified");

                        EvenIdsPaxIdsVO evenIdsPaxIdsVO1 = fbEventsService.getEvenIdsPaxIdsVO(eventId);
                        if(evenIdsPaxIdsVO1 != null) {
                            evenIdsPaxIdsVO.setId(evenIdsPaxIdsVO1.getId());
                        }
                        evenIdsPaxIdsVOS.add(evenIdsPaxIdsVO);
                        fbEventsService.saveEventPaxSortedDetails(evenIdsPaxIdsVO);

                    }
                }
            }
        }
        return evenIdsPaxIdsVOS;
    }


    private boolean checkEventMutuallyAccepted(String eventId) {
        EvenIdsPaxIdsVO tempObj = fbEventsService.getEvenIdsPaxIdsVO(eventId);
        boolean isFirstAccepted = false;
        boolean isSecondAccepted = false;
        if (tempObj != null && tempObj.getPaxOne().keySet().size() == 2) {
            int i = 0;
            for (String key : tempObj.getPaxOne().keySet()) {
                i++;
                if(i == 1) {
                    isFirstAccepted = tempObj.getPaxOne().get(key);
                } else if (i == 2){
                    isSecondAccepted = tempObj.getPaxOne().get(key);
                }
            }
        }
        return isFirstAccepted && isSecondAccepted;
    }

    public void populateEventMatching() {
        List<FBEvents> fbEventsList = fbEventsService.getAllFBEvents();
        for (FBEvents fbEvents : fbEventsList) {
            EventDetails eventDetails = fbEventsService.getEventDetails(fbEvents.getEventId());
            if (eventDetails != null) {
                eventDetails.getUserIds().put(fbEvents.getUserId(), false);
                fbEventsService.saveEventDetails(eventDetails);
            } else if (eventDetails == null) {
                eventDetails = new EventDetails();
                eventDetails.setEventId(fbEvents.getEventId());
                HashMap<String, Boolean> userIds = new HashMap<>();
                userIds.put(fbEvents.getUserId(), false);
                eventDetails.setUserIds(userIds);
                fbEventsService.saveEventDetails(eventDetails);
            }
        }
    }


    public void saveUserEvents() {
        List<User> list = fbUsersService.getUsers();
        for (User user : list) {
            List<FBEvents> fbEventsList = fbEventsService.getEvents(user.getUserId(), user.getAccessToken());
            for (FBEvents fbEvents : fbEventsList) {
                FBEvents fbEventsFind = fbEventsService.getFbEvent(fbEvents.getEventId());
                    fbEvents.setUserId(user.getUserId());
                    fbEventsService.save(fbEvents);

            }
        }
    }
}