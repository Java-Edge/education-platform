package com.javaedge.back.service;

import com.alibaba.fastjson.JSONArray;
import com.javaedge.back.entity.Career;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
public interface CareerService extends IService<Career> {

    JSONArray getData();
}
