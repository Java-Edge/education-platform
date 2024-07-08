package com.javagpt.back.handler;

import com.javagpt.common.resp.ResultBody;
import com.javagpt.common.constant.SystemConstant;
import com.javagpt.common.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 * 服务侧原因异常返回500， 用户侧原因异常返回400， 授权相关返回401
 * 详情错误可参考 CommonError 和 SystemConstant定义
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     *
     * @return 接口失败信息
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResultBody bizExceptionHandler(HttpServletRequest req, BizException e) {
        // 业务异常无需打印详细错误日志
        log.error("请求：{} 发生业务异常！原因是：{}", req.getRequestURI(), e.getErrorMsg());
        return ResultBody.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理@Valid参数错误异常
     *
     * @return 接口失败信息
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBody MethodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {
        log.error("请求：{} 发生參數异常!", req.getRequestURI());
        return ResultBody.error(SystemConstant.PARAMETER_INVALID_CODE, e.getLocalizedMessage());
    }

    /**
     * 处理系统异常
     *
     * @return 接口失败信息
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBody ExceptionHandler(HttpServletRequest req, Exception e) {
        log.error("请求：{} 异常！原因：", req.getRequestURI(), e);
        return ResultBody.error(SystemConstant.DEFAULT_FAIL_CODE, e.getMessage());
    }
}
