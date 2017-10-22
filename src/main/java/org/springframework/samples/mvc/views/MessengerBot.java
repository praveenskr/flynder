package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by psekar on 10/22/17.
 */
@Component
public class MessengerBot {

    public static String base_url = "https://murmuring-sierra-83851.herokuapp.com/push";

    private static String page_access_token = "EAAByTJgYNysBAMjUhREhX0ZBOHqFQjMZCs6CJGWGkZCcg3yHgbYwLl4Up3TE2j4aX12tVx52MfqRdWFVfJ8WfExbX7rQkgRXYeZAGHPZCAjgiK4yQIXBAYwrOINQliGibpAKsrJaS5zwkryLOje2UreY0tdEJebqwzKTnKqtikjz53DnCceVN";

    

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FBUsersDao fbUsersDao;
    


    public boolean initiateFirstMessageToUser(String mobile, String message) {
        String INTIAL_BOT_MESSAGE_BODY = "{\"recipient\":{\"phone_number\":\"{mobile}\"},\"message\":{\"text\":\"{text_message}\"}}";
        RestTemplate restTemplate = new RestTemplate();
        INTIAL_BOT_MESSAGE_BODY = INTIAL_BOT_MESSAGE_BODY.replace("{mobile}", mobile);
        INTIAL_BOT_MESSAGE_BODY = INTIAL_BOT_MESSAGE_BODY.replace("{text_message}", message);
        List<FBEvents> fbEvents = null;
        StringBuffer url = new StringBuffer(base_url);
        //url.append(page_access_token);
        FirstMessengerBotMessage firstMessengerBotMessage = null;
        try {
            firstMessengerBotMessage = objectMapper.readValue(INTIAL_BOT_MESSAGE_BODY, FirstMessengerBotMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResponseEntity<FirstMessengerBotMessageResponse> responseEntity = restTemplate.postForEntity(url.toString(), firstMessengerBotMessage, FirstMessengerBotMessageResponse.class, new HashMap<>());
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            User user = fbUsersDao.getUserByMobile(firstMessengerBotMessage.getRecipient().getPhoneNumber());
            user.setMessengerUserId(responseEntity.getBody().getRecipientId());
            return true;
        } else {
            System.out.println("First Notify user failed");
            return false;
        }
    }

}
