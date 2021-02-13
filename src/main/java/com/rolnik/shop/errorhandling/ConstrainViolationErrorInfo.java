package com.rolnik.shop.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.ConstraintViolation;
import javax.validation.Path;

@AllArgsConstructor
@Data

public class ConstrainViolationErrorInfo {
    private final String className;
    private final String property;
    private final String invalidValue;
    private final String message;

    public ConstrainViolationErrorInfo(ConstraintViolation<?> violation) {
        this.className = violation.getLeafBean().getClass().getSimpleName();
        this.property = resolvePropertyName(violation.getPropertyPath());
        this.invalidValue = violation.getInvalidValue().toString();
        this.message = violation.getMessage();
    }

    private String resolvePropertyName(Path path) {
        String value = null;

        for (Path.Node node : path) {
            value = node.getName();
        }

        return value;
    }
}
