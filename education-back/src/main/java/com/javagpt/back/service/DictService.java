package com.javagpt.back.service;

import com.javagpt.back.entity.Dictionary;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.vo.MenuVO;

import java.util.List;

public interface DictService extends IService<Dictionary> {

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



    List<DictionaryType> listByTypeKeys(List<String> typeKeys);

}
