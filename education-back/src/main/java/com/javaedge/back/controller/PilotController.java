package com.javaedge.back.controller;

import com.javaedge.back.dto.CourseQueryDTO;
import com.javaedge.back.entity.Pilot;
import com.javaedge.common.req.PageQueryParam;
import com.javaedge.common.resp.ResultBody;
import com.javaedge.back.service.PilotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import java.util.List;
import java.util.Map;

/**
 * 导航工具
 */
@RestController
@RequestMapping("/pilot")
@Slf4j
public class PilotController {

    @Autowired
    private PilotService pilotService;

    @SentinelResource(value = "pilot_list", 
                     blockHandler = "blockHandler")
    @GetMapping("/getList")
    public ResultBody list() {
        Map<String, List<Pilot>> pilotServiceList = pilotService.getList();
        return ResultBody.success(pilotServiceList);
    }

    @PostMapping("/pv")
    public ResultBody pv(@RequestBody PageQueryParam<CourseQueryDTO> pageQueryParam) {
        pilotService.pv(pageQueryParam.getParam().getItemId());
        return ResultBody.success();
    }

    // 限流降级方法
    public ResultBody blockHandler(BlockException e) {
        log.warn("触发限流", e);
        return ResultBody.error("服务繁忙，请稍后再试");
    }
}