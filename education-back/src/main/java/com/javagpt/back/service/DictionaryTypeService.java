package com.javagpt.back.service;

import com.javagpt.back.entity.DictionaryType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
public interface DictionaryTypeService extends IService<DictionaryType> {

    DictionaryType selectList(String typeKey);
}
