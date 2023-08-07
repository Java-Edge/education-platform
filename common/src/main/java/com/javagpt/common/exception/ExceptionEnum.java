package com.javagpt.common.exception;


/**
 * 异常枚举类
 */
public enum ExceptionEnum implements IErrorCode {

    INVALID_REQUEST_PARAMETER(10001, "无效请求参数"),  //无效请求参数
    ;

    private int code;
    private String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
