package com.javagpt.common.resp;

import com.javagpt.common.constant.ErrorCodeConstant;
import com.javagpt.common.exception.IErrorCode;
import lombok.Data;

/**
 * @author JavaEdge
 */
@Data
public class RespResult<T> implements ErrorCodeConstant {

    private Boolean success;

    private String msg;

    private Integer code;

    private T data;

    public static RespResult success() {
        return success(null);
    }

    public static RespResult success(Object data) {
        RespResult respResult = new RespResult();
        respResult.setSuccess(true);
        respResult.setCode(DEFAULT_SUCCESS_ERROR_CODE);
        respResult.setMsg(DEFAULT_SUCCESS_MSG);
        respResult.setData(data);
        return respResult;
    }


    public static RespResult fail(IErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMsg());
    }

    public static RespResult fail(String msg) {
        return fail(DEFAULT_FAIL_ERROR_CODE, msg);
    }

    public static RespResult fail(Integer code, String msg) {
        RespResult respResult = new RespResult();
        respResult.setSuccess(false);
        respResult.setCode(code);
        respResult.setMsg(msg);
        return respResult;
    }


}
