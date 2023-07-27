package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.InterviewArticleDto;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.entity.InterviewExperienceArticleEntity;
import com.javagpt.back.service.InterviewExperienceArticleService;
import com.javagpt.back.mapper.InterviewExperienceArticleMapper;
import com.javagpt.back.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author MSIK
* @description 针对表【interview_experience_article】的数据库操作Service实现
* @createDate 2023-07-24 23:36:50
*/
@Service
public class InterviewExperienceArticleServiceImpl extends ServiceImpl<InterviewExperienceArticleMapper, InterviewExperienceArticleEntity>
    implements InterviewExperienceArticleService{

    @Autowired
    private InterviewExperienceArticleMapper articleMapper;

    @Override
    public IPage<ArticleVO> selectByCondition(PageQueryParam<InterviewArticleDto> pageQueryParam) {

        Page<ArticleVO> page = new Page<>();
        page.setSize(pageQueryParam.getPageSize());
        page.setCurrent(pageQueryParam.getPageNo());
        if (pageQueryParam.getParam() == null){
            pageQueryParam.setParam(new InterviewArticleDto());
        }
        IPage<ArticleVO> articleVOS = articleMapper.selectByCondition(pageQueryParam.getParam(), page);

        return articleVOS;
    }
}




