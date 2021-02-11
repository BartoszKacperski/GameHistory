package com.rolnik.shop.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class EntityNotFoundErrorInfo implements ErrorInfo {
    private final String className;
}
