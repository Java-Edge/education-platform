package com.javagpt.common.exception;


import com.javagpt.common.constant.ErrorCodeConstant;

/**
 * 通用错误接口
 */
public interface IErrorCode extends ErrorCodeConstant {

    /**
     * 返回错误代码
     *
     * @return
     */
    int getCode();

    /**
     * 返回错误消息
     *
     * @return
     */
    String getMsg();

}
