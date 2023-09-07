package com.javagpt.interviewspider.entity.service;

import com.javagpt.interviewspider.entity.ArticleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;

/**
* @author MSIK
* @description 针对表【interview_experience_article】的数据库操作Service
* @createDate 2023-07-24 22:25:39
*/
@Mapper
public interface ArticleService extends IService<ArticleEntity> {

}
