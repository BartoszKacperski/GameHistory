package com.rolnik.shop.errorhandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ApiError {
    private HttpStatus status;
    private String message;
    private ErrorInfo errorInfo;
    private String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime errorDate;

    public ApiError(HttpStatus status, String message, ErrorInfo errorInfo, String url) {
        this.status = status;
        this.message = message;
        this.errorInfo = errorInfo;
        this.url = url;
        this.errorDate = LocalDateTime.now();
    }
}
