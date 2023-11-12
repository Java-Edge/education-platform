package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javagpt.back.converter.CourseConverter;
import com.javagpt.back.dto.SpecialQueryDTO;
import com.javagpt.back.service.SpecialColumnService;
import com.javagpt.back.vo.course.CourseVO;
import com.javagpt.back.vo.course.SpecialColumnVO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 专栏页面入口
 */
@RestController
@RequestMapping("/course")
public class SpecialColumnController {

    @Resource
    private SpecialColumnService specialColumnService;


    /**
     * 获取专栏列表
     *
     * @return 全部专栏
     */
    @PostMapping("/special/search")
    public ResultBody specialList(@RequestBody PageQueryParam<SpecialQueryDTO> pageQueryParam) {
        Page<CourseVO> courseVOPage = specialColumnService.pageListSpecial(pageQueryParam);
        List<SpecialColumnVO> specialColumnVOS = Lists.newArrayList();
        for (CourseVO courseVO: courseVOPage.getRecords()) {
            specialColumnVOS.add(CourseConverter.INSTANCE.toVO(courseVO));
        }
        IPage<SpecialColumnVO> specialColumnVOPage = new Page<>();
        specialColumnVOPage.setRecords(specialColumnVOS);
        specialColumnVOPage.setTotal(courseVOPage.getTotal());
        return ResultBody.success(specialColumnVOPage);
    }

    /**
     * 统计 pv 的接口
     */
    @PostMapping("/special/pv")
    public ResultBody pv(@RequestBody PageQueryParam<SpecialQueryDTO> pageQueryParam) {
        specialColumnService.pv(pageQueryParam.getParam().getItemId());
        return ResultBody.success();
    }
}
