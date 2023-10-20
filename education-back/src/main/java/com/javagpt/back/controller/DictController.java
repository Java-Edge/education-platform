package com.javagpt.back.controller;

import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.DictionaryType;
import com.javagpt.back.service.DictionaryTypeService;
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

    @GetMapping("/list")
    public ResultBody list(@RequestParam(name = "typeKey") String typeKey) {
        DictionaryType dictionaryType = dictTypeService.selectList(typeKey);
        return ResultBody.success(dictionaryType);
    }

    @GetMapping("/listByMultiTypeKey")
    public ResultBody listByTypeKeys(@RequestParam(name = "typeKeys") List<String> typeKeys) {
        List<DictionaryType> dictionaryTypes = dictTypeService.listByTypeKeys(typeKeys);
        return ResultBody.success(dictionaryTypes);
    }

}

