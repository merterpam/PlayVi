package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserModel> findAll();

    Optional<UserModel> findById(String id);

    UserModel createUser(String username);

    UserModel saveUser(UserModel user);

    void delete(UserModel user);

    void setCurrentUser(String userId, HttpServletResponse response);

    Optional<UserModel> getCurrentUser(HttpServletRequest request);
}
