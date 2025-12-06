package com.javaedge.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.javaedge.back.converter.SidelineConverter;
import com.javaedge.back.dto.CourseQueryDTO;
import com.javaedge.back.entity.Sideline;
import com.javaedge.back.service.SidelineService;
import com.javaedge.back.vo.SidelineVO;
import com.javaedge.common.req.PageQueryParam;
import com.javaedge.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sideline")
public class SidelineController {

    @Autowired
    private SidelineService sidelineService;

    @GetMapping("/getByPage")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<Sideline> sidelinePage = sidelineService.selectPage(current,size);

        List<SidelineVO> sidelineVOS = Lists.newArrayList();
        for (Sideline sidelineVO: sidelinePage.getRecords()) {
            sidelineVOS.add(SidelineConverter.INSTANCE.toVO(sidelineVO));
        }
        IPage<SidelineVO> sidelineVOPage = new Page<>();
        sidelineVOPage.setRecords(sidelineVOS);
        sidelineVOPage.setTotal(sidelinePage.getTotal());
        return ResultBody.success(sidelineVOPage);
    }

    /**
     * 先注释掉接口，防止被查询隐私数据
     */
    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Integer id) {
        Sideline sideline = sidelineService.getById(id);
        return ResultBody.success(sideline);
    }

    /**
     * 统计 pv 的接口
     */
    @PostMapping("/pv")
    public ResultBody pv(@RequestBody PageQueryParam<CourseQueryDTO> pageQueryParam) {
        sidelineService.pv(pageQueryParam.getParam().getItemId());
        return ResultBody.success();
    }


}
