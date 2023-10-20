package com.javagpt.back.controller;


import cn.hutool.bloomfilter.bitMap.BitMap;
import cn.hutool.bloomfilter.bitMap.IntMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.service.DictionaryService;
import com.javagpt.back.service.DictionaryTypeService;
import com.javagpt.back.service.impl.DictionaryServiceImpl;
import com.javagpt.back.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("/list")
    public ResultBody list(@RequestParam(name = "typeKey") String typeKey) {
        DictionaryType dictionaryType = dictionaryTypeService.selectList(typeKey);
        return ResultBody.success(dictionaryType);
    }

    @GetMapping("/menuList")
    public ResultBody menuList(@RequestParam(name = "typeKey") String typeKey) {
        List<MenuVO> menuVOList = dictionaryService.selectMenuList(typeKey);
        return ResultBody.success(menuVOList);
    }


    @GetMapping("/childMenuList")
    public ResultBody childMenuList(@RequestParam(name = "typeKey") String typeKey, @RequestParam(name = "parentId") Integer parentId) {
        List<MenuVO> menuVOList = dictionaryService.selectChildMenuList(typeKey, parentId);
        return ResultBody.success(menuVOList);
    }


    @GetMapping("/listByMultiTypeKey")
    public ResultBody listByMultiTypeKey(@RequestParam(name = "typeKeys") List<String> typeKeys) {
        List<DictionaryType> dictionaryTypes = dictionaryTypeService.selectListByMultiTypeKey(typeKeys);
        return ResultBody.success(dictionaryTypes);
    }

}

