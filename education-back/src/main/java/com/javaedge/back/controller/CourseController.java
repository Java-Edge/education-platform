package com.javaedge.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javaedge.back.converter.CourseConverter;
import com.javaedge.back.dto.CourseQueryDTO;
import com.javaedge.back.entity.CoursePO;
import com.javaedge.back.service.CourseService;
import com.javaedge.back.service.VideoService;
import com.javaedge.back.vo.course.CourseVO;
import com.javaedge.back.vo.course.SpecialColumnVO;
import com.javaedge.common.req.PageQueryParam;
import com.javaedge.common.resp.ResultBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程分为视频和专栏
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    private final VideoService videoService;

    /**
     * 获取专栏列表
     *
     * @return 全部专栏
     */
    @PostMapping("/special/search")
    public ResultBody specialList(@RequestBody PageQueryParam<CourseQueryDTO> pageQueryParam) {
        Page<CourseVO> courseVOPage = courseService.pageQuery(pageQueryParam);
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
    public ResultBody pv(@RequestBody PageQueryParam<CourseQueryDTO> pageQueryParam) {
        courseService.pv(pageQueryParam.getParam().getItemId());
        return ResultBody.success();
    }

    @GetMapping("/courseList")
    public ResultBody courseList() {
        List<CoursePO> list = videoService.selectList();
        return ResultBody.success(list);
    }

    @GetMapping("/video/{id}")
    public ResultBody getById(@PathVariable("id") Integer id) {
        CoursePO entity = videoService.getById(id);
        return ResultBody.success(entity);
    }

    @GetMapping("/getFiveCourse")
    public ResultBody getFiveCourse() {
        List<CoursePO> list = videoService.getFiveCourse();
        return ResultBody.success(list);
    }

    @GetMapping("/getRecommendCourses")
    public ResultBody getRecommendCourses() {
        List<CoursePO> list = videoService.getRecommendCourses();
        return ResultBody.success(list);
    }
}