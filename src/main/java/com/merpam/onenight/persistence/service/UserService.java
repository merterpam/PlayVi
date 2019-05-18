package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.UserModel;

import java.util.List;

public interface UserService {

    List<UserModel> findAll();

    UserModel findById(String id);

    UserModel createUser(String username);

    void delete(UserModel user);
}
