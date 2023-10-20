package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.mapper.DictionaryMapper;
import com.javagpt.back.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.vo.MenuVO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictService {


    @Autowired
    private DictionaryMapper dictionaryMapper;


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


}
