package com.rolnik.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FinishingNotOwnCurrentGameException extends RuntimeException {

    public FinishingNotOwnCurrentGameException() {
    }

    public FinishingNotOwnCurrentGameException(String message) {
        super(message);
    }

    public FinishingNotOwnCurrentGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public FinishingNotOwnCurrentGameException(Throwable cause) {
        super(cause);
    }

    public FinishingNotOwnCurrentGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
