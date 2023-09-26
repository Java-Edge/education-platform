package com.javagpt.back.dto;

public class ResultStatus {

    public static final int OK = 200;
    public static final int NO = 10001;

    public static final int LOGIN_SUCCESS = 400;  //认证成功
    public static final int LOGIN_FAIL_NOT = 401; //用户未登录
    public static final int LOGIN_FAIL_OVERDUE = 402; //用户登录失效

    public static final int AUTHORIZE_FAIL = 403; //用户登录失效

    /**
     * 用户不存在
     */
    public static final int USER_NOT_FOUND = 400001;


    /**
     * 用户密码错误
     */
    public static final int USER_ERROR_PASSWORD = 400002;

    /**
     * 验证码错误
     */
    public static final int ERROR_CODE = 400003;



}