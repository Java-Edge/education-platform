package com.javagpt.back.controller;

import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.Ranking;
import com.javagpt.back.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 千祎来了
 * @date 2023/8/8 15:11
 */
@RestController
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping("/getRanking")
    public ResultBody getRanking() {
        List<Ranking> rankings = rankingService.getRanking();
        return ResultBody.success(rankings);
    }

}
