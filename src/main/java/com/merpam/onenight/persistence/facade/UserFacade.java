package com.merpam.onenight.persistence.facade;

import com.merpam.onenight.dto.UserDTO;
import com.merpam.onenight.persistence.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserFacade {

    /**
     * Creates the user from the given DTO
     *
     * @param user is the user DTO
     * @return the created user
     */
    UserDTO createUser(UserDTO user);

    /**
     * Gets the current user
     *
     * @param request is the request which will contain information about the current user
     * @return the current user
     */
    UserModel getCurrentUser(HttpServletRequest request);

    /**
     * Sets the given user as current user
     *
     * @param userId   is the id of the user
     * @param response is the response which will contain information about the current user
     */
    void setCurrentUser(String userId, HttpServletResponse response);

    /**
     * Replaces the party of the user with a new one
     *
     * @param partyId is the new party id
     * @param user    is the user
     */
    void replaceParty(String partyId, UserModel user);
}
