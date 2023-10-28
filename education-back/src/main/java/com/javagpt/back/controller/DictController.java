package com.javagpt.back.controller;

import com.javagpt.back.vo.SuperMenuVO;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.service.DictService;
import com.javagpt.back.service.DictionaryTypeService;
import com.javagpt.back.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictController {

    @Autowired
    private DictionaryTypeService dictTypeService;

    @Autowired
    private DictService dictService;

    @GetMapping("/list")
    public ResultBody list(@RequestParam(name = "typeKey") String typeKey) {
        DictionaryType dictionaryType = dictTypeService.selectList(typeKey);
        return ResultBody.success(dictionaryType);
    }

    @GetMapping("/menuList")
    public ResultBody menuList(@RequestParam(name = "typeKey") String typeKey) {
        List<MenuVO> menuVOList = dictService.selectMenuList(typeKey);
        return ResultBody.success(menuVOList);
    }


    @GetMapping("/childMenuList")
    public ResultBody childMenuList(@RequestParam(name = "typeKey") String typeKey, @RequestParam(name = "parentId") Integer parentId) {
        List<MenuVO> menuVOList = dictService.selectChildMenuList(typeKey, parentId);
        return ResultBody.success(menuVOList);
    }

    @GetMapping("/superMenuList")
    public ResultBody superMenuList(@RequestParam(name = "typeKey") String typeKey) {
        List<SuperMenuVO> menuVOList = dictService.selectSuperMenuList(typeKey);
        return ResultBody.success(menuVOList);
    }

    @GetMapping("/listByMultiTypeKey")
    public ResultBody listByTypeKeys(@RequestParam(name = "typeKeys") List<String> typeKeys) {
        List<DictionaryType> dictionaryTypes = dictTypeService.listByTypeKeys(typeKeys);
        return ResultBody.success(dictionaryTypes);
    }
}

