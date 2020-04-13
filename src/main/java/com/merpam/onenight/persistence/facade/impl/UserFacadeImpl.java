package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.dto.UserDTO;
import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotFoundException;

@Service
public class UserFacadeImpl implements UserFacade {

    private UserService userService;

    @Override
    public UserDTO createUser(UserDTO user) {
        UserModel userModel = getUserService().createUser(user.getUsername());
        //TODO use converter
        user.setId(userModel.getId());
        return user;
    }

    @Override
    public void setCurrentUser(String userId, final HttpServletResponse response) {
        getUserService().setCurrentUser(userId, response);
    }

    @Override
    public UserModel getCurrentUser(final HttpServletRequest request) {
        return getUserService().getCurrentUser(request)
                .orElseThrow(() -> new NotFoundException("Current user cannot be found"));
    }

    @Override
    public void replaceParty(String partyId, UserModel user) {
        user.setPartyId(partyId);
        userService.saveUser(user);
    }

    protected UserService getUserService() {
        return userService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
