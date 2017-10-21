package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by psekar on 10/21/17.
 */
public class FBEvents {

    @JsonProperty("paging")
    FBEventsPaging fbEventsPaging;

    public FBEventsPaging getFbEventsPaging() {
        return fbEventsPaging;
    }

    public void setFbEventsPaging(FBEventsPaging fbEventsPaging) {
        this.fbEventsPaging = fbEventsPaging;
    }
}
