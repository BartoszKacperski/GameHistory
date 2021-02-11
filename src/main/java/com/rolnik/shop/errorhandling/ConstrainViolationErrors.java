package com.rolnik.shop.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data

public class ConstrainViolationErrors implements ErrorInfo {
    private List<ConstrainViolationErrorInfo> errors;
}
