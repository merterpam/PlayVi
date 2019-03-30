package com.merpam.onenight.persistence.service.impl;

import com.merpam.onenight.persistence.dao.UserDao;
import com.merpam.onenight.persistence.model.User;
import com.merpam.onenight.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public User createUser(String username) {
        return userDao.save(new User(username));
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
