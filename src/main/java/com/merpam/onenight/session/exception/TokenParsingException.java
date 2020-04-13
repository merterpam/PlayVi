package com.merpam.onenight.session.exception;

public class TokenParsingException extends RuntimeException {

    public TokenParsingException() {
        super();
    }

    public TokenParsingException(String message) {
        super(message);
    }

    public TokenParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenParsingException(Throwable cause) {
        super(cause);
    }
}
