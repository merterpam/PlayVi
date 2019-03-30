package com.merpam.onenight.persistence.service;

import com.merpam.onenight.persistence.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User createUser(String username);

    void delete(User user);
}
