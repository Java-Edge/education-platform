package com.javagpt.common.resp;

import com.javagpt.common.bos.BaseInfoInterface;
import com.javagpt.common.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用响应体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultBody {
 
    //响应代码
    private int code;
 
    //响应消息
    private String message;
 
    //响应结果
    private Object result;
 
    public ResultBody(BaseInfoInterface infoInterface) {
        this.code = infoInterface.getResultCode();
        this.message = infoInterface.getResultMsg();
    }

    public static ResultBody success(Object data) {
        ResultBody body = new ResultBody();
        body.setCode(CommonEnum.SUCCESS.getResultCode());
        body.setMessage(CommonEnum.SUCCESS.getResultMsg());
        body.setResult(data);
        return body;
    }

    public static ResultBody success() {
        ResultBody body = new ResultBody();
        body.setCode(CommonEnum.SUCCESS.getResultCode());
        body.setMessage(CommonEnum.SUCCESS.getResultMsg());
        return body;
    }

    public static ResultBody error(BaseInfoInterface infoInterface) {
        ResultBody body = new ResultBody();
        body.setCode(infoInterface.getResultCode());
        body.setMessage(infoInterface.getResultMsg());
        body.setResult(null);
        return body;
    }

    public static ResultBody error(int code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    public static ResultBody error(String message) {
        ResultBody body = new ResultBody();
        body.setCode(-1);
        body.setMessage(message);
        body.setResult(null);
        return body;
    }

}