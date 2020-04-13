package com.merpam.onenight.session.exception;

public class TokenGeneratıonException extends RuntimeException {

    public TokenGeneratıonException() {
        super();
    }

    public TokenGeneratıonException(String message) {
        super(message);
    }

    public TokenGeneratıonException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenGeneratıonException(Throwable cause) {
        super(cause);
    }
}
