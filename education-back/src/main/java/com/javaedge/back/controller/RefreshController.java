package com.javaedge.back.controller;

import com.javaedge.back.service.ArticleService;
import com.javaedge.back.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 强制刷新指定表数据到本地缓存
 */
@RestController
@RequestMapping("/refresh")
public class RefreshController {

    @Autowired
    private PilotService pilotService;

    /**
     * 刷新导航表
     */
    @PostMapping("/pilot")
    public void save() {
        pilotService.refresh();
    }
}
