package com.javagpt.back.controller;

import com.javagpt.back.dto.CourseQueryDTO;
import com.javagpt.back.entity.Pilot;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.service.PilotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("getList")
    public ResultBody list() {
        Map<String, List<Pilot>> pilotServiceList = pilotService.getList();
        return ResultBody.success(pilotServiceList);
    }


    /**
     * 统计 pv 的接口
     */
    @PostMapping("/pv")
    public ResultBody pv(@RequestBody PageQueryParam<CourseQueryDTO> pageQueryParam) {
        pilotService.pv(pageQueryParam.getParam().getItemId());
        return ResultBody.success();
    }
}

