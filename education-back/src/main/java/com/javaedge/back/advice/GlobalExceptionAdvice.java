package com.javaedge.back.advice;

import com.javaedge.common.exception.BusinessRuntimeException;
import com.javaedge.common.exception.Error401RuntimeException;
import com.javaedge.common.exception.Error403RuntimeException;
import com.javaedge.common.exception.Error404RuntimeException;
import com.javaedge.common.resp.ResultBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author JavaEdge
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultBody exception(Exception e, HttpServletResponse response) {
        if (!response.isCommitted()) {
            // 只有响应未提交时才写入
            return ResultBody.error("系统异常");
        }
        return null;
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> exception(UsernameNotFoundException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", e.getCause());
        result.put("msg", e.getMessage());
        return result;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResultBody exception(RuntimeException e) {
        log.error("RuntimeException ex:", e);
        return ResultBody.error(e.getMessage());
    }

    /**
     * 处理参数验证异常
     * valid注解 和 bean上的注解结合
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResultBody bindException(BindException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("global BindException:", e);
        return ResultBody.error("参数校验失败,字段:" + fieldError.getField() + ",值为:" + fieldError.getRejectedValue() + ",原因:" + fieldError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultBody missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("global MissingServletRequestParameterException:", e);
        return ResultBody.error("参数校验失败,参数:" + e.getParameterName() + ",值不能为空");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBody methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("global MethodArgumentNotValidException:", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        return ResultBody.error("参数校验失败,字段:" + fieldError.getField() + ",值为:" + fieldError.getRejectedValue() + ",原因:" + fieldError.getDefaultMessage());
    }

    /**
     * 处理参数验证异常
     * <p>
     * validate注解controller和当个参数验证
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResultBody validationException(ValidationException e) {
        log.error("global ValidationException:", e);
        return ResultBody.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessRuntimeException.class)
    public ResultBody businessRuntimeException(BusinessRuntimeException e) {
        log.error("global BusinessRuntimeException:", e);
        return ResultBody.error(e.getErrorCode(), e.getMsg());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Error404RuntimeException.class)
    public ResultBody error404RuntimeException(Error404RuntimeException e) {
        return ResultBody.error(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(Error401RuntimeException.class)
    public ResultBody error401RuntimeException(Error401RuntimeException e) {
        return ResultBody.error(HttpStatus.UNAUTHORIZED.value(), "请登录！");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResultBody accessDeniedException(AccessDeniedException e) {
        return ResultBody.error(HttpStatus.FORBIDDEN.value(), "对不起，您没有权限，请联系管理员！");
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(Error403RuntimeException.class)
    public ResultBody Error403RuntimeException(Error403RuntimeException e) {
        return ResultBody.error(HttpStatus.FORBIDDEN.value(), "对不起，您没有权限，请联系管理员！");
    }

    /**
     * 接口 method不支持，会报错
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBody httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("global HttpRequestMethodNotSupportedException:", e);
        return ResultBody.error("此请求方法[" + e.getMethod() + "]不支持");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResultBody DuplicateKeyException(DuplicateKeyException e) {
        log.error("global DuplicateKeyException:", e);
        return ResultBody.error("数据已存在！");
    }
}