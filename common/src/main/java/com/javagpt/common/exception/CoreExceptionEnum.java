package com.javagpt.common.exception;

/**
 * 异常枚举类
 */
public enum CoreExceptionEnum implements IErrorCode {

    //错误码定义规则
    //调度程序和cube server程序 公共部分错误码定义以3000开头
    //调度程序错误码定义以600开头
    //cube server程序错误码定义以100开头

    //公共
    INVALID_REQUEST_PARAMETER(10001, "无效请求参数:{0}", "prompt"),  //无效请求参数
    LOGIN_INVALID(10002, "登录已失效，请刷新页面或重新登录！", "error"),
    NO_AUTH(10005, "您没有权限访问，请联系管理员！", "error"),
    IO_EXCEPTION(10006, "IO异常", "error"),

    ;

    private int code;
    private String msg;
    private String level;

    CoreExceptionEnum(int code, String msg, String level) {
        this.code = code;
        this.msg = msg;
        this.level = level;
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

    public String getLevel() {
        return level;
    }
}
