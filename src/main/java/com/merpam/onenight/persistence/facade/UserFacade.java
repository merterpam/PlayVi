package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.dto.UserDTO;
import com.merpam.onenight.persistence.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserFacade {
    UserDTO createUser(UserDTO user);

    void setCurrentUser(String userId, HttpServletResponse response);

    UserModel getCurrentUser(HttpServletRequest request);

    void replaceParty(String partyId, UserModel user);
}
