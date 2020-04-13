package com.merpam.onenight.session.strategy.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.merpam.onenight.session.SecurityConstants;
import com.merpam.onenight.session.exception.TokenGeneratıonException;
import com.merpam.onenight.session.exception.TokenParsingException;
import com.merpam.onenight.session.strategy.TokenGenerator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtTokenGenerator implements TokenGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenGenerator.class);

    private static int MAX_AGE = 24 * 60 * 60 * 1000;

    @Override
    public <T> String generateToken(T value) throws TokenGeneratıonException {
        try {
            String jsonValue = new ObjectMapper().writeValueAsString(value);

            byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();
            String token = Jwts.builder()
                    .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                    .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                    .setIssuer(SecurityConstants.TOKEN_ISSUER)
                    .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                    .setSubject(jsonValue)
                    .setExpiration(new Date(System.currentTimeMillis() + MAX_AGE))
                    .compact();

            return SecurityConstants.TOKEN_AUTHORIZATION_PREFIX + token;
        } catch (JsonProcessingException e) {
            throw new TokenGeneratıonException(e);
        }
    }

    @Override
    public <T> T parseToken(String token, Class<T> valueType) throws TokenParsingException {
        try {
            if (token == null) {
                throw new IllegalArgumentException("Empty token");
            }

            byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

            Jws<Claims> parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace("Bearer ", ""));

            String jsonValue = parsedToken.getBody().getSubject();

            return new ObjectMapper().readValue(jsonValue, valueType);
        } catch (ExpiredJwtException | MalformedJwtException | IOException | DecodingException ex) {
            LOG.debug("Token cannot be parsed", ex);
            throw new TokenParsingException("Token cannot be parsed");
        }
    }
}
