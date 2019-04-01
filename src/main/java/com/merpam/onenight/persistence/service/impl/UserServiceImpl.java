package com.merpam.onenight.persistence.service.impl;

import com.merpam.onenight.persistence.dao.UserDao;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.persistence.service.UserService;
import com.merpam.onenight.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Override
    public List<UserModel> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserModel findById(String id) {
        if(id == null) {
            return null;
        }

        return userDao.findById(id).orElse(null);
    }

    @Override
    public UserModel createUser(String username) {
        return userDao.save(new UserModel(username, DateUtils.getCurrentTimestampInSeconds()));
    }

    @Override
    public void delete(UserModel user) {
        userDao.delete(user);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
