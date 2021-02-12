package com.rolnik.shop.errorhandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

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

    public ApiError(HttpStatus status, String message, ErrorInfo errorInfo, WebRequest webRequest) {
        this.status = status;
        this.message = message;
        this.errorInfo = errorInfo;
        this.url = webRequest instanceof ServletWebRequest ? ((ServletWebRequest)webRequest).getRequest().getRequestURL().toString() : "";
        this.errorDate = LocalDateTime.now();
    }
}
