package com.javagpt.back.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Dictionary;
import com.javagpt.back.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("list")
    public ResultBody list(String typeKey){
        List<Dictionary> list = dictionaryService.selectList(typeKey);
        return ResultBody.success(list);
    }

}

