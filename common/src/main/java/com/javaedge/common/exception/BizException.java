package com.javaedge.common.exception;

import com.javaedge.common.constant.SystemConstant;
import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    protected int errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public BizException() {
        super(SystemConstant.FAIL_MSG);
        this.errorCode = SystemConstant.DEFAULT_FAIL_CODE;
        this.errorMsg = SystemConstant.FAIL_MSG;
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorCode = SystemConstant.DEFAULT_FAIL_CODE;
        this.errorMsg = errorMsg;
    }

    public BizException(CommonError commonError) {
        super(commonError.getMessage());
        this.errorCode = commonError.getCode();
        this.errorMsg = commonError.getMessage();
    }

    public BizException(int code , String msg) {
        this.errorCode = code;
        this.errorMsg = msg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
