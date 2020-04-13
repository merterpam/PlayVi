package com.merpam.onenight.session.service.impl;

import com.merpam.onenight.session.SecurityConstants;
import com.merpam.onenight.session.exception.TokenParsingException;
import com.merpam.onenight.session.model.SessionUser;
import com.merpam.onenight.session.service.SessionService;
import com.merpam.onenight.session.strategy.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionServiceImpl implements SessionService {

    private static final Logger LOG = LoggerFactory.getLogger(SessionServiceImpl.class);

    private TokenGenerator tokenGenerator;

    @Override
    public String getCurrentUserIdFromSession(final HttpServletRequest request) {

        try {
            String token = getToken(request, SecurityConstants.TOKEN_AUTHORIZATION_HEADER);

            SessionUser sessionUser = tokenGenerator.parseToken(token, SessionUser.class);
            return sessionUser.getId();

        } catch (TokenParsingException ex) {
            LOG.debug("Token Parsing Exception", ex);
            return null;
        }
    }

    @Override
    public void setCurrentUserIdToSession(String currentUserId, final HttpServletResponse response) {
        String token = getTokenGenerator().generateToken(new SessionUser(currentUserId));

        response.addHeader(SecurityConstants.TOKEN_AUTHORIZATION_HEADER,
                SecurityConstants.TOKEN_AUTHORIZATION_PREFIX + token);
    }

    private String getToken(HttpServletRequest request, String tokenHeader) {
        return request.getHeader(tokenHeader);
    }

    protected TokenGenerator getTokenGenerator() {
        return tokenGenerator;
    }

    @Autowired
    public void setTokenGenerator(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }
}
