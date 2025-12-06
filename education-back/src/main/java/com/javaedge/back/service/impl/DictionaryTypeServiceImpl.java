package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.javaedge.back.entity.Dictionary;
import com.javaedge.back.entity.DictionaryType;
import com.javaedge.back.mapper.DictTypeMapper;
import com.javaedge.back.service.DictService;
import com.javaedge.back.service.DictionaryTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.javaedge.common.constant.CommonConstants.cache_max_dict_local_cache;
import static com.javaedge.common.constant.CommonConstants.type_key;

@Service
public class DictionaryTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictionaryType> implements DictionaryTypeService {

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictService dictService;

    @Resource
    private Cache<String, List<DictionaryType>> dictTypeRefreshCache;

    private static final String status = "status";


    @Override
    public DictionaryType selectList(String typeKey) {
        QueryWrapper<DictionaryType> qw = new QueryWrapper<>();
        qw.eq(type_key, typeKey);
        // 查询有效的字典
        qw.eq(status, "1");
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
