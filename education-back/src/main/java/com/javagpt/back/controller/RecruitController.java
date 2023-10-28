package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javagpt.back.converter.RecruitConverter;
import com.javagpt.back.dto.RecruitQO;
import com.javagpt.back.entity.RecruitPO;
import com.javagpt.back.service.RecruitService;
import com.javagpt.back.vo.RecruitEntity;
import com.javagpt.back.vo.RecruitVO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/recruit")
public class RecruitController {

    @Resource
    private RecruitService recruitService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size, HttpServletRequest request) {
        Page<RecruitPO> page = recruitService.selectPage(current, size, request);
        return ResultBody.success(page);
    }

    @PostMapping("/selectByCondition")
    public ResultBody list(@RequestBody PageQueryParam<RecruitQO> pageQueryParam, HttpServletRequest request) {
        Page<RecruitEntity> recruitEntityPage = recruitService.selectByCondition(pageQueryParam, request);

        List<RecruitVO> articleVOS = Lists.newArrayList();
        for (RecruitEntity recruitEntity: recruitEntityPage.getRecords()) {
            articleVOS.add(RecruitConverter.INSTANCE.toVO(recruitEntity));
        }
        IPage<RecruitVO> recruitVOPage = new Page<>();
        recruitVOPage.setRecords(articleVOS);
        recruitVOPage.setTotal(recruitEntityPage.getTotal());
        return ResultBody.success(recruitVOPage);
    }

    @PostMapping("/save")
    public ResultBody save(@RequestBody RecruitPO recruit) {
        recruit.setCreateTime(new Date());
        recruitService.save(recruit);
        return ResultBody.success("保存成功");
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable String id) {
        RecruitEntity recruitEntity = recruitService.queryById(id);
        return ResultBody.success(recruitEntity);
    }


    @GetMapping("/hotRecruits/")
    public ResultBody hotRecruits() {
        List<RecruitPO> hotRecruits = recruitService.getHotRecruits();
        return ResultBody.success(hotRecruits);
    }

}

