package com.javagpt.back.mapper;

import com.javagpt.back.entity.Dictionary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.MenuVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {

    /**
     * 查询菜单列表
     *
     * @param typeKey
     * @return
     */
    List<MenuVO> selectMenuList(String typeKey);

    /**
     * 查询子菜单列表
     *
     * @param typeKey
     * @param parentId
     * @return
     */
    List<MenuVO> selectChildMenuList(String typeKey, Integer parentId);
}
