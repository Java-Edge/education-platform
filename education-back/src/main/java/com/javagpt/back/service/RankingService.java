package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.Ranking;

public interface RankingService extends IService<Ranking> {

    Page<Ranking> getRanking(Integer current, Integer size);
}
