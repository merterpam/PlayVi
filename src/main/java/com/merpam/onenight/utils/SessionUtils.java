package com.merpam.onenight.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merpam.onenight.session.SecurityConstants;
import com.merpam.onenight.session.SessionUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public final class SessionUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SessionUtils.class);

    private static int MAX_AGE = 24 * 60 * 60 * 1000;

    private SessionUtils() {
        //empty constructor, not meant to be initialized
    }

    public static void setSessionUser(HttpServletResponse response, SessionUser user) throws JsonProcessingException {

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
        String jsonUser = new ObjectMapper().writeValueAsString(user);

        String token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(jsonUser)
                .setExpiration(new Date(System.currentTimeMillis() + MAX_AGE))
                .compact();

        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
    }

    public static SessionUser getSessionUser(HttpServletRequest request) throws IOException {

        try {
            String token = request.getHeader(SecurityConstants.TOKEN_HEADER);

            if (token == null) {
                return null;
            }

            byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

            Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace("Bearer ", ""));

            String jsonUser = parsedToken
                    .getBody()
                    .getSubject();

            return new ObjectMapper().readValue(jsonUser, SessionUser.class);
        } catch (ExpiredJwtException | MalformedJwtException ex) {
            LOG.info("Bad token", ex);
            return null;
        }
    }

    public static SessionUser generateSessionUser(String id, String partyId, String username) {
        return new SessionUser(id, partyId, username);
    }
}
