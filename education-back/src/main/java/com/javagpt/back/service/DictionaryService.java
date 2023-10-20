package com.javagpt.back.service;

import com.javagpt.back.entity.Dictionary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.vo.MenuVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zqy
 * @since 2023-07-28
 */
public interface DictionaryService extends IService<Dictionary> {

    List<Dictionary> selectList(String typeKey);

    /**
     * 查询菜单列表
     *
     * @param typeKey
     * @return
     */
    List<MenuVO> selectMenuList(String typeKey);

    /**
     * 查询菜单列表
     *
     * @param typeKey
     * @return
     */
    List<MenuVO> selectChildMenuList(String typeKey,Integer parentId);

}
