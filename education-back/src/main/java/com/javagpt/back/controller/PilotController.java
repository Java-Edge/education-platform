package com.javagpt.back.controller;

import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.service.PilotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

