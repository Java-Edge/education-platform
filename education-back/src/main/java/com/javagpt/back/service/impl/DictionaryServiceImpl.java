package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.mapper.DictTypeMapper;
import com.javagpt.back.mapper.DictMapper;
import com.javagpt.back.service.DictService;
import com.javagpt.back.vo.MenuVO;
import com.javagpt.back.vo.SuperMenuVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.javagpt.common.constant.Constants.cache_max_dict_local_cache;

@Service
public class DictionaryServiceImpl extends ServiceImpl<DictMapper, Dictionary> implements DictService {


    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictMapper dictionaryMapper;

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
        return dictionaryMapper.selectChildMenuList(typeKey, parentId);
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

    /**
     * 采用循环方式替代递归方式，提高查询效率
     * <p>
     * 1. 抽出当前等级的菜单
     * 2. 抽出上一级别的菜单
     * 3. 将当前别菜单组装到当上一级别菜单
     * 4. 是否执行到最后底部菜单，是结束循环，否则继续第一步
     *
     * @param typeKey 菜单编号
     * @return 三级及以上菜单列表
     */
    @Override
    public List<SuperMenuVO> selectSuperMenuList(String typeKey) {
        List<SuperMenuVO> menuVOList = dictionaryMapper.selectSuperMenuList(typeKey);
        final Integer deLevel = menuVOList.stream().max(Comparator.comparing(SuperMenuVO::getLevel)).orElse(null).getLevel();
        List<SuperMenuVO> currentMenuList;
        List<SuperMenuVO> parentMenuList = menuVOList.stream().filter(item -> Objects.equals(item.getLevel(), deLevel)).collect(Collectors.toList());
        for (int i = deLevel - 1; i > 0; i--) {
            int level = i;
            currentMenuList = parentMenuList;
            parentMenuList = menuVOList.stream().filter(item -> item.getLevel() == level).collect(Collectors.toList());
            for (SuperMenuVO parentMenu : parentMenuList) {
                List<SuperMenuVO> menuList = currentMenuList.stream().filter(item -> item.getParentId().equals(parentMenu.getId())).collect(Collectors.toList());
                parentMenu.setChildren(menuList);
            }
        }
        return parentMenuList;
    }

}
