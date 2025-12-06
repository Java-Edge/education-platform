package com.javaedge.common.exception;


import com.javaedge.common.constant.SystemConstant;

public enum CommonError {

    PARAM_INVALID(SystemConstant.PARAMETER_INVALID_CODE, "缺少必要參數")

    ;


    private final int code;
    private final String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    CommonError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
