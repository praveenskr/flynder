package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by psekar on 10/22/17.
 */
public class FirstMessengerBotMessage {

    @JsonProperty("recipient")
    Recipient recipient;

    @JsonProperty("message")
    Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
}
