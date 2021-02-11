package com.rolnik.shop.errorhandling;

import com.rolnik.shop.exceptions.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        final List<ConstrainViolationErrorInfo> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(new ConstrainViolationErrorInfo(violation));
        }

        final ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST,
                "constraintViolationException",
                new ConstrainViolationErrors(errors),
                request
        );

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(final EntityNotFoundException ex, final WebRequest request) {
        final ApiError apiError = new ApiError(
                HttpStatus.NOT_FOUND,
                "entityNotFound",
                new EntityNotFoundErrorInfo(
                        ex.getClazz().getSimpleName()
                ),
                request
        );

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleUnknownException(final Exception exception, final WebRequest request) {
        final ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                null,
                request
        );

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ApiError apiError = new ApiError(
                status,
                ex.getMessage(),
                null,
                request
        );

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
