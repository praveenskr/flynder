package org.springframework.samples.mvc.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by psekar on 10/22/17.
 */
@Component
public class SchedulerTask {

    @Autowired
    FBUsersService fbUsersService;

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        fbUsersService.down();
    }
}
