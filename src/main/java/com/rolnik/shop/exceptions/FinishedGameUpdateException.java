package com.rolnik.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FinishedGameUpdateException extends RuntimeException {

    public FinishedGameUpdateException() {
        super();
    }

    public FinishedGameUpdateException(String message) {
        super(message);
    }

    public FinishedGameUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FinishedGameUpdateException(Throwable cause) {
        super(cause);
    }

    protected FinishedGameUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
