package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by psekar on 10/21/17.
 */
public class FBEventsPagingCursors {

    @JsonProperty("before")
    String before;

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
