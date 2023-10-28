package com.javagpt.back.mapper;

import com.javagpt.back.entity.Dictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.MenuVO;
import com.javagpt.back.vo.SuperMenuVO;

import java.util.List;

public interface DictMapper extends BaseMapper<Dictionary> {

    /**
     * 查询菜单列表
     */
    List<MenuVO> selectMenuList(String typeKey);

    /**
     * 查询子菜单列表
     */
    List<MenuVO> selectChildMenuList(String typeKey, Integer parentId);

    /**
     * 三级以上菜单全部查询
     *
     * @param typeKey
     * @return
     */
    List<SuperMenuVO> selectSuperMenuList(String typeKey);
}
