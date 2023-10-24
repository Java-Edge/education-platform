package com.javagpt.back.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javagpt.common.constant.Constants;
import com.javagpt.common.constant.ResultStatus;
import com.javagpt.common.resp.ResultBody;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.PrintWriter;

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
