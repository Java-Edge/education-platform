package com.javagpt.back.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 千祎来了
 * @date 2023/10/24 23:35
 */
@RestControllerAdvice
public class GExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public void handleRRException(AccessDeniedException e) {
        throw e;
    }
}
