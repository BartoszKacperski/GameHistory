package com.rolnik.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CurrentGameAlreadyExistsException extends RuntimeException {

    public CurrentGameAlreadyExistsException() {
        super();
    }

    public CurrentGameAlreadyExistsException(String message) {
        super(message);
    }

    public CurrentGameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrentGameAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected CurrentGameAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
