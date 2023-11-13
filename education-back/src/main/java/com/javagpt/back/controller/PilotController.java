package com.javagpt.back.controller;

import com.javagpt.back.dto.SpecialQueryDTO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.service.PilotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultBody getList() {
        return ResultBody.success(pilotService.getList());
    }


    /**
     * 统计 pv 的接口
     */
    @PostMapping("/pv")
    public ResultBody pv(@RequestBody PageQueryParam<SpecialQueryDTO> pageQueryParam) {
        pilotService.pv(pageQueryParam.getParam().getItemId());
        return ResultBody.success();
    }
}

