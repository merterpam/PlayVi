package com.merpam.onenight.persistence.facade.impl;

import com.merpam.onenight.persistence.facade.UserFacade;
import com.merpam.onenight.persistence.dao.UserDao;
import com.merpam.onenight.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFacadeImpl implements UserFacade {

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
