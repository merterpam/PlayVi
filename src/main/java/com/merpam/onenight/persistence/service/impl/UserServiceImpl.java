package com.merpam.onenight.persistence.service.impl;

import com.merpam.onenight.persistence.dao.UserDao;
import com.merpam.onenight.persistence.model.User;
import com.merpam.onenight.persistence.service.UserService;
import com.merpam.onenight.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User createUser(String username) {
        return userDao.save(new User(username, DateUtils.getCurrentTimestampInSeconds()));
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
