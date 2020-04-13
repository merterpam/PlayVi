package com.merpam.onenight.session.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SessionService {

    String getCurrentUserIdFromSession(HttpServletRequest request);

    void setCurrentUserIdToSession(String id, HttpServletResponse response);
}
