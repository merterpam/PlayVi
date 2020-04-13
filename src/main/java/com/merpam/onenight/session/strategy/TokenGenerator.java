package com.merpam.onenight.session.strategy;

import com.merpam.onenight.session.exception.TokenGeneratıonException;
import com.merpam.onenight.session.exception.TokenParsingException;

public interface TokenGenerator {
    <T> String generateToken(T value) throws TokenGeneratıonException;

    <T> T parseToken(String token, Class<T> valueType) throws TokenParsingException;
}
