package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFacadeImpl implements UserFacade {

    private UserService userService;

    @Override
    public UserModel createUser(String username) {
        return userService.createUser(username);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
