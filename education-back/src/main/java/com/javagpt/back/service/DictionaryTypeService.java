package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.DictionaryType;

import java.util.List;

public interface DictionaryTypeService extends IService<DictionaryType> {

    DictionaryType selectList(String typeKey);

    List<DictionaryType> listByTypeKeys(List<String> typeKeys);
}
