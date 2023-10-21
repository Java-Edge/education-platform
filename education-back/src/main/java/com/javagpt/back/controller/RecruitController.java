package com.javagpt.back.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.back.dto.RecruitDTO;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.entity.Recruit;
import com.javagpt.back.service.RecruitService;
import com.javagpt.back.vo.RecruitVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size, HttpServletRequest request) {
        Page<Recruit> page = recruitService.selectPage(current, size, request);
        return ResultBody.success(page);
    }

    @PostMapping("/selectByCondition")
    public ResultBody list(@RequestBody PageQueryParam<RecruitDTO> pageQueryParam, HttpServletRequest request) {
        Page<RecruitVO> recruitVOPage = recruitService.selectByCondition(pageQueryParam, request);
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

