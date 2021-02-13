package com.rolnik.shop.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class BasicErrorInfo implements ErrorInfo {
    private final String message;
}
