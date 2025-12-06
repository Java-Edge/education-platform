package com.javaedge.common.resp;

import com.javaedge.common.constant.ErrorCodeConstant;
import com.javaedge.common.exception.IErrorCode;
import lombok.Data;

/**
 * @author JavaEdge
 */
@Data
public class GenericResponse<T> implements ErrorCodeConstant {

    private Boolean success;

    private String msg;

    private Integer code;

    private T data;

    public static GenericResponse success() {
        return success(null);
    }

    public static GenericResponse success(Object data) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(true);
        genericResponse.setCode(DEFAULT_SUCCESS_ERROR_CODE);
        genericResponse.setMsg(DEFAULT_SUCCESS_MSG);
        genericResponse.setData(data);
        return genericResponse;
    }


    public static GenericResponse fail(IErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMsg());
    }

    public static GenericResponse fail(String msg) {
        return fail(DEFAULT_FAIL_ERROR_CODE, msg);
    }

    public static GenericResponse fail(Integer code, String msg) {
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setSuccess(false);
        genericResponse.setCode(code);
        genericResponse.setMsg(msg);
        return genericResponse;
    }


}
