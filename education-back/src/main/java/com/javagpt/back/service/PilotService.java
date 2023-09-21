package com.javagpt.back.service;

import com.javagpt.back.entity.Pilot;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-09-12
 */
public interface PilotService extends IService<Pilot> {

    Map<String, List<Pilot>> getList();
}
