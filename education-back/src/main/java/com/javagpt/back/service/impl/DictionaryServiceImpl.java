package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.mapper.DictTypeMapper;
import com.javagpt.back.mapper.DictionaryMapper;
import com.javagpt.back.service.DictService;
import com.javagpt.back.vo.MenuVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.javagpt.common.constant.Constants.cache_max_dict_local_cache;

@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictService {


    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictionaryMapper dictionaryMapper;

    @Resource
    private Cache<String, List<DictionaryType>> dictTypeRefreshCache;


    @Override
    public List<Dictionary> selectList(String typeKey) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_key", typeKey);
        queryWrapper.eq("status", 1);
        queryWrapper.orderByAsc("sort");
        List<Dictionary> dictionaries = this.getBaseMapper().selectList(queryWrapper);
        return dictionaries;
    }

    @Override
    public List<MenuVO> selectMenuList(String typeKey) {
        return dictionaryMapper.selectMenuList(typeKey);

    }

    @Override
    public List<MenuVO> selectChildMenuList(String typeKey, Integer parentId) {
        return dictionaryMapper.selectChildMenuList(typeKey,parentId);
    }

    @Override
    public List<DictionaryType> listByTypeKeys(List<String> typeKeys) {
        List<DictionaryType> dictTypes = dictTypeRefreshCache.get(cache_max_dict_local_cache, s -> {
            QueryWrapper<DictionaryType> dictTypeQW = new QueryWrapper<>();
            dictTypeQW.in("type_key", typeKeys);
            return dictTypeMapper.selectList(dictTypeQW);
        });

        for (DictionaryType dictType : dictTypes) {
            String typeKey = dictType.getTypeKey();
            List<Dictionary> dictList4TypeKey = selectList(typeKey);
            dictType.setList(dictList4TypeKey);
        }
        return dictTypes;
    }

}
