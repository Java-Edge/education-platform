package com.javaedge.back.service;

import com.javaedge.back.entity.UserIntegral;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javaedge.common.resp.ResultBody;

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
