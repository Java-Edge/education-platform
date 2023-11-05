package com.javagpt.back.service;

import com.javagpt.back.entity.UserIntegral;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.common.resp.ResultBody;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-10-26
 */
public interface UserIntegralService extends IService<UserIntegral> {

    ResultBody sign(Integer userId);

}
