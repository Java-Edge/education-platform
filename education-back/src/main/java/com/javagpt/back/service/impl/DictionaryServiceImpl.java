package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.mapper.DictionaryMapper;
import com.javagpt.back.mapper.DictionaryTypeMapper;
import com.javagpt.back.service.DictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {


    @Override
    public List<Dictionary> selectList(String typeKey) {
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type_key", typeKey);
        queryWrapper.eq("status", 1);
        queryWrapper.orderByAsc("sort");
        List<Dictionary> dictionaries = this.getBaseMapper().selectList(queryWrapper);
        return dictionaries;
    }
}
