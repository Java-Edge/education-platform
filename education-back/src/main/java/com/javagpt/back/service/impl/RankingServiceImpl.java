package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Ranking;
import com.javagpt.back.mapper.ArticleMapper;
import com.javagpt.back.mapper.RankingMapper;
import com.javagpt.back.service.ArticleService;
import com.javagpt.back.service.RankingService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MSIK
 * @description 针对表【source_course】的数据库操作Service实现
 * @createDate 2023-07-09 13:40:08
 */
@Service
public class RankingServiceImpl extends ServiceImpl<RankingMapper, Ranking>
        implements RankingService {

    @Autowired
    private RankingMapper rankingMapper;

    @Override
    public List<Ranking> getRanking() {
        QueryWrapper<Ranking> qw = new QueryWrapper<>();
        qw.orderByAsc("order_val");
        qw.last("limit 10");
        List<Ranking> rankings = rankingMapper.selectList(qw);
        if (rankings == null) {
            return null;
        }
        for (int i = 0; i < rankings.size(); i ++) {
            rankings.get(i).setOrderVal(i+1);
        }
        return rankings;
    }
}




