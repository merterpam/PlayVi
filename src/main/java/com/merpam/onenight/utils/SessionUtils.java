package com.merpam.onenight.utils;

import com.merpam.onenight.session.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public final class SessionUtils {

    private static String USER = "user";
    private static int MAX_AGE = 24 * 60 * 60;

    private SessionUtils() {
        //empty constructor, not meant to be initialized
    }

    public static String setSessionUser(HttpServletRequest request, SessionUser user) {
        HttpSession session = request.getSession();
        session.setAttribute(USER, user);
        session.setMaxInactiveInterval(MAX_AGE);
        return session.getId();
    }

    public static SessionUser getSessionUser(HttpServletRequest request) {
        return Optional.ofNullable(request.getSession(false)).map(r -> (SessionUser) r.getAttribute(USER)).orElse(null);
    }

    public static SessionUser generateSessionUser(String id, String partyId, String username) {
        return new SessionUser(id, partyId, username);
    }
}
