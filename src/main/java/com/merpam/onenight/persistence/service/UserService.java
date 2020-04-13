package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Finds all users
     *
     * @return list of users
     */
    List<UserModel> findAll();

    /**
     * Creates a user
     *
     * @param username is the username of the user
     * @return the created user
     */
    UserModel createUser(String username);

    /**
     * Saves the user
     *
     * @param user is the user to be saved
     * @return the saved user
     */
    UserModel saveUser(UserModel user);

    /**
     * Deletes the user
     *
     * @param user is the user to be deleted
     */
    void delete(UserModel user);

    /**
     * Gets the current user
     *
     * @param request is the request which will contain information about the current user
     * @return the current user
     */
    Optional<UserModel> getCurrentUser(HttpServletRequest request);

    /**
     * Sets the given user as current user
     *
     * @param userId   is the id of the user
     * @param response is the response which will contain information about the current user
     */
    void setCurrentUser(String userId, HttpServletResponse response);
}
