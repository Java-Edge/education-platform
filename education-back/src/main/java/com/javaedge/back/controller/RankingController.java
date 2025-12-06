package com.javaedge.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.back.dto.CourseQueryDTO;
import com.javaedge.common.req.PageQueryParam;
import com.javaedge.common.resp.ResultBody;
import com.javaedge.back.entity.Ranking;
import com.javaedge.back.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 排行榜页面入口
 *
 * @author 千祎来了
 * @date 2023/8/8 15:11
 */
@RestController
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/getRanking")
    public ResultBody getRanking(Integer current, Integer size) {
        Page<Ranking> page = rankingService.getRanking(current, size);
        return ResultBody.success(page);
    }

    /**
     * 统计 pv 的接口
     */
    @PostMapping("/pv")
    public ResultBody pv(@RequestBody PageQueryParam<CourseQueryDTO> pageQueryParam) {
        rankingService.pv(pageQueryParam.getParam().getItemId());
        return ResultBody.success();
    }

}
