package com.rolnik.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserCredentialsAlreadyInUseException extends RuntimeException {

    public UserCredentialsAlreadyInUseException() {
    }

    public UserCredentialsAlreadyInUseException(String message) {
        super(message);
    }

    public UserCredentialsAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCredentialsAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public UserCredentialsAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
