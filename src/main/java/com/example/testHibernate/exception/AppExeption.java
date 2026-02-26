package com.example.testHibernate.exception;

import com.example.testHibernate.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExeption {
    @ExceptionHandler(value = RuntimeException.class)
    public ApiResponse<String> handleException(RuntimeException e) {
        return ApiResponse.<String>builder()
                .data(e.getMessage())
                .code(999)
                .build();
    }
}
