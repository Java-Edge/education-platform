package com.javagpt.application.dict.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.javagpt.domain.entity.Dictionary;
import com.javagpt.domain.entity.DictionaryType;
import com.javagpt.back.service.DictService;
import com.javagpt.back.service.DictionaryTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.javagpt.common.constant.Constants.cache_max_dict_local_cache;
import static com.javagpt.common.constant.Constants.type_key;

@Service
public class DictionaryTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictionaryType> implements DictionaryTypeService {

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictService dictService;

    @Resource
    private Cache<String, List<DictionaryType>> dictTypeRefreshCache;



    @Override
    public DictionaryType selectList(String typeKey) {
        QueryWrapper<DictionaryType> qw = new QueryWrapper<>();
        qw.eq(type_key, typeKey);
        DictionaryType dictionaryType = dictTypeMapper.selectOne(qw);
        List<Dictionary> dictionaries = dictService.selectList(typeKey);
        dictionaryType.setList(dictionaries);
        return dictionaryType;
    }

    @Override
    public List<DictionaryType> listByTypeKeys(List<String> typeKeys) {
        List<DictionaryType> dictTypes = dictTypeRefreshCache.get(cache_max_dict_local_cache, s -> {
            QueryWrapper<DictionaryType> dictTypeQW = new QueryWrapper<>();
            dictTypeQW.in(type_key, typeKeys);
            return dictTypeMapper.selectList(dictTypeQW);
        });

        for (DictionaryType dictType : dictTypes) {
            String typeKey = dictType.getTypeKey();
            List<Dictionary> dictList4TypeKey = dictService.selectList(typeKey);
            dictType.setList(dictList4TypeKey);
        }
        return dictTypes;
    }


}
