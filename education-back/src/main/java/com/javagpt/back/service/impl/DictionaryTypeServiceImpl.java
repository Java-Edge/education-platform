package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.mapper.DictionaryTypeMapper;
import com.javagpt.back.service.DictionaryService;
import com.javagpt.back.service.DictionaryTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
@Service
public class DictionaryTypeServiceImpl extends ServiceImpl<DictionaryTypeMapper, DictionaryType> implements DictionaryTypeService {

    @Resource
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Resource
    private DictionaryService dictionaryService;

    @Override
    public DictionaryType selectList(String typeKey) {
        QueryWrapper<DictionaryType> qw = new QueryWrapper<>();
        qw.eq("type_key", typeKey);
        DictionaryType dictionaryType = dictionaryTypeMapper.selectOne(qw);
        List<Dictionary> dictionaries = dictionaryService.selectList(typeKey);
        dictionaryType.setList(dictionaries);
        return dictionaryType;
    }
}
