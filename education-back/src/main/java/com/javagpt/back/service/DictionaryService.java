package com.javagpt.back.service;

import com.javagpt.back.entity.Dictionary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
public interface DictionaryService extends IService<Dictionary> {

    List<Dictionary> selectList(String typeKey);
}
