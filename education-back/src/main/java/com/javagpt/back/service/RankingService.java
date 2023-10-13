package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.Ranking;

import java.util.List;

public interface RankingService extends IService<Ranking> {

    List<Ranking> getRanking();
}
