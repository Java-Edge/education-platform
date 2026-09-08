package com.javaedge.growth.exception;

import com.javaedge.common.exception.BizException;

/**
 * 会员成长领域异常基类。
 *
 * <p>继承公共 {@link BizException}，保证全局异常处理器能统一拦截，
 * 同时把"成长域"相关的错误类型收敛到本包内，对外只暴露领域语义异常。
 */
public class MemberGrowthException extends BizException {

    public MemberGrowthException(String msg) {
        super(msg);
    }

    public MemberGrowthException(int code, String msg) {
        super(code, msg);
    }
}
