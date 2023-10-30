package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.Sideline;
import com.javagpt.back.service.SidelineService;
import com.javagpt.common.resp.ResultBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sideline")
public class SidelineController {

    @Autowired
    private SidelineService sidelineService;

    @GetMapping("/getByPage")
//    @PreAuthorize("@ss.hasRole('admin,vip')")
    public ResultBody getByPage(Integer current, Integer size) {
        Page<Sideline> page = sidelineService.selectPage(current,size);
        return ResultBody.success(page);
    }

    /**
     * 先注释掉接口，防止被查询隐私数据
     */
//    @GetMapping("/getById/{id}")
//    public ResultBody getById(@PathVariable Integer id) {
//        Sideline sideline = sidelineService.getById(id);
//        return ResultBody.success(sideline);
//    }
}
