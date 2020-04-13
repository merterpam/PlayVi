package com.merpam.onenight.persistence.service.impl;

import com.merpam.onenight.persistence.dao.UserDao;
import com.merpam.onenight.persistence.model.UserModel;
import com.merpam.onenight.persistence.service.UserService;
import com.merpam.onenight.session.service.SessionService;
import com.merpam.onenight.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    private SessionService sessionService;

    @Override
    public List<UserModel> findAll() {
        return getUserDao().findAll();
    }

    @Override
    public UserModel createUser(String username) {
        return getUserDao().insert(new UserModel(username, DateUtils.getCurrentTimestampInSeconds()));
    }

    @Override
    public UserModel saveUser(UserModel user) {
        return getUserDao().save(user);
    }

    @Override
    public void setCurrentUser(String userId, final HttpServletResponse response) {
        getSessionService().setCurrentUserIdToSession(userId, response);
    }

    @Override
    public Optional<UserModel> getCurrentUser(final HttpServletRequest request) {
        final String currentUserId = getSessionService().getCurrentUserIdFromSession(request);

        return Optional.ofNullable(currentUserId).flatMap(getUserDao()::findById);
    }

    @Override
    public void delete(UserModel user) {
        getUserDao().delete(user);
    }

    protected SessionService getSessionService() {
        return sessionService;
    }

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    protected UserDao getUserDao() {
        return userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
