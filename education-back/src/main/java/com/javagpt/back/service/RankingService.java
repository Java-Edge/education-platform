package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.entity.Ranking;

import java.util.List;

/**
* @author MSIK
* @description 针对表【source_course】的数据库操作Service
* @createDate 2023-07-09 13:40:08
*/
public interface RankingService extends IService<Ranking> {

    List<Ranking> getRanking();
}
