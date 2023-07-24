package com.javagpt.back.dto;

import com.javagpt.back.entity.CommonEnum;
import com.javagpt.back.service.BaseInfoInterface;

/**
 * 自定义返回数据格式
 */
public class ResultBody {
 
    //响应代码
    private int code;
 
    //响应消息
    private String message;
 
    //响应结果
    private Object result;
 
    public ResultBody() {
    }
 
    public ResultBody(BaseInfoInterface infoInterface) {
        this.code = infoInterface.getResultCode();
        this.message = infoInterface.getResultMsg();
    }
 
    public int getCode() {
        return code;
    }
 
    public void setCode(int code) {
        this.code = code;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public Object getResult() {
        return result;
    }
 
    public void setResult(Object result) {
        this.result = result;
    }
 
    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static ResultBody success(Object data) {
        ResultBody body = new ResultBody();
        body.setCode(CommonEnum.SUCCESS.getResultCode());
        body.setMessage(CommonEnum.SUCCESS.getResultMsg());
        body.setResult(data);
        return body;
    }

    /**
     * 成功
     *
     * @return
     */
    public static ResultBody success() {
        ResultBody body = new ResultBody();
        body.setCode(CommonEnum.SUCCESS.getResultCode());
        body.setMessage(CommonEnum.SUCCESS.getResultMsg());
        return body;
    }

    /**
     * 失败
     *
     * @param infoInterface
     * @return
     */
    public static ResultBody error(BaseInfoInterface infoInterface) {
        ResultBody body = new ResultBody();
        body.setCode(infoInterface.getResultCode());
        body.setMessage(infoInterface.getResultMsg());
        body.setResult(null);
        return body;
    }
 
    /**
     * 失败
     */
    public static ResultBody error(int code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }
 
    /**
     * 失败
     * @param message
     * @return
     */
    public static ResultBody error(String message) {
        ResultBody body = new ResultBody();
        body.setCode(-1);
        body.setMessage(message);
        body.setResult(null);
        return body;
    }

}