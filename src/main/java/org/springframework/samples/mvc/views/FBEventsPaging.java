package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by psekar on 10/21/17.
 */
public class FBEventsPaging {

    @JsonProperty("cursors")
    FBEventsPagingCursors fbEventsPagingCursors;

    public FBEventsPagingCursors getFbEventsPagingCursors() {
        return fbEventsPagingCursors;
    }

    public void setFbEventsPagingCursors(FBEventsPagingCursors fbEventsPagingCursors) {
        this.fbEventsPagingCursors = fbEventsPagingCursors;
    }
}
