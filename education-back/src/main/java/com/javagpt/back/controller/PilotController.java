package com.javagpt.back.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Pilot;
import com.javagpt.back.service.PilotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.javagpt.common.constant.Constants.cache_max_pilot_refresh_counts;

/**
 * 导航工具
 */
@RestController
@RequestMapping("/pilot")
@Slf4j
public class PilotController {

    @Autowired
    private PilotService pilotService;

    @Autowired
    private Cache<Integer, List<Pilot>> refreshCountsCache;

    @GetMapping("getList")
    public ResultBody getList() {
        List<Pilot> localCache = refreshCountsCache.get(cache_max_pilot_refresh_counts, s -> {
            log.info("开始获取缓存");
            return (List<Pilot>) pilotService.getList();
        });
        return ResultBody.success(localCache);
    }

}

