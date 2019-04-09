package com.merpam.onenight.scheduler;

import com.merpam.onenight.constants.Constants;
import com.merpam.onenight.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExpiredUserDeletionScheduler {

    private UserService userService;

    @Scheduled(cron = "0 0 0 * * *")
    public void runExpiredUserDeletionScheduler() {
        Date today = new Date();
        userService.findAll()
                .stream()
                .filter(user -> user.getTimestamp() < today.getTime() - Constants.EXPIRATION_INTERVAL)
                .forEach(userService::delete);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
