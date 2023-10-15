package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.Project;
import com.javagpt.back.entity.Ranking;
import com.javagpt.back.mapper.RankingMapper;
import com.javagpt.back.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingServiceImpl extends ServiceImpl<RankingMapper, Ranking> implements RankingService {

    @Autowired
    private RankingMapper rankingMapper;

    @Override
    public Page<Ranking> getRanking(Integer current, Integer size) {
        Page<Ranking> page = new Page<>(current, size);
        QueryWrapper<Ranking> qw = new QueryWrapper<>();
        qw.orderByAsc("order_val");
        Page<Ranking> rankingPage = rankingMapper.selectPage(page, qw);
        List<Ranking> records = rankingPage.getRecords();
        if (records == null) {
            return null;
        }
        for (int i = 0; i < records.size(); i++) {
            records.get(i).setOrderVal(i + 1);
        }
        return rankingPage;
    }
}




