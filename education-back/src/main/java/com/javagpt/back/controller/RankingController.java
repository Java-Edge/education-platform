package com.javagpt.back.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.CourseQueryDTO;
import com.javagpt.common.req.PageQueryParam;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.back.entity.Ranking;
import com.javagpt.back.service.RankingService;
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
