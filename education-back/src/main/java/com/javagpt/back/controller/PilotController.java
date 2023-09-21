package com.javagpt.back.controller;


import com.javagpt.back.entity.Pilot;
import com.javagpt.back.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zqy
 * @since 2023-09-12
 */
@RestController
@RequestMapping("/pilot")
public class PilotController {

    @Autowired
    private PilotService pilotService;

    @GetMapping("getList")
    public List<Pilot> getList() {
        return pilotService.getList();
    }


}

