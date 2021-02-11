package com.rolnik.shop.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.ConstraintViolation;

@AllArgsConstructor
@Data

public class ConstrainViolationErrorInfo {
    private final String className;
    private final String property;
    private final String invalidValue;

    public ConstrainViolationErrorInfo(ConstraintViolation<?> violation) {
        this.className = violation.getRootBeanClass().getSimpleName();
        this.property = violation.getPropertyPath().toString();
        this.invalidValue = violation.getInvalidValue().toString();
    }
}
