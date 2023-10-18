package com.javagpt.back.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.dto.RecruitDTO;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Recruit;
import com.javagpt.back.service.RecruitService;
import com.javagpt.back.vo.RecruitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<Recruit> page = recruitService.selectPage(current, size);
        return ResultBody.success(page);
    }

    @PostMapping("/selectByCondition")
    public ResultBody list(@RequestBody PageQueryParam<RecruitDTO> pageQueryParam) {
        Page<RecruitVO> recruitVOPage = recruitService.selectByCondition(pageQueryParam);
        return ResultBody.success(recruitVOPage);
    }

    @PostMapping("/save")
    public ResultBody save(@RequestBody Recruit recruit) {
        recruit.setCreateTime(new Date());
        recruitService.save(recruit);
        return ResultBody.success("保存成功");
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        RecruitVO recruitVO = recruitService.queryById(id);
        return ResultBody.success(recruitVO);
    }


    @GetMapping("/hotRecruits/")
    public ResultBody hotRecruits() {
        List<Recruit> hotRecruits = recruitService.getHotRecruits();
        return ResultBody.success(hotRecruits);
    }

}

