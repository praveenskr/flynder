package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by psekar on 10/22/17.
 */
public class Recipient {
    @JsonProperty("phone_number")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
