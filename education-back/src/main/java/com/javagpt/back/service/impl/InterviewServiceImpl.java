package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.ArticleDTO;
import com.javagpt.back.dto.PageQueryParam;
import com.javagpt.back.entity.Career;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.back.mapper.CareerMapper;
import com.javagpt.back.service.CareerService;
import com.javagpt.back.service.InterviewService;
import com.javagpt.back.mapper.InterviewMapper;
import com.javagpt.back.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author MSIK
* @description 针对表【interview_experience_article】的数据库操作Service实现
* @createDate 2023-07-24 23:36:50
*/
@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, InterviewEntity>
    implements InterviewService {

    @Autowired
    private InterviewMapper articleMapper;

    @Autowired
    private CareerMapper careerMapper;

    @Override
    public IPage<ArticleVO> selectByCondition(PageQueryParam<ArticleDTO> pageQueryParam) {
        // 如果传入的 jobId 是 -1 表示查询全部岗位
        if (pageQueryParam.getParam() != null && pageQueryParam.getParam().getJobId() != null && pageQueryParam.getParam().getJobId() == -1) {
            pageQueryParam.getParam().setJobId(null);
        }

        Page<ArticleVO> page = new Page<>();
        page.setSize(pageQueryParam.getPageSize());
        page.setCurrent(pageQueryParam.getPageNo());
        if (pageQueryParam.getParam() == null){
            pageQueryParam.setParam(new ArticleDTO());
        }
        IPage<ArticleVO> articleVOS = articleMapper.selectByCondition(pageQueryParam.getParam(), page);
        return articleVOS;
    }

    @Override
    public InterviewEntity articleOf(String id) {
        InterviewEntity article = getById(id);
        Integer jobId = article.getJobId();
        if (jobId != null) {
            Career byId = careerMapper.selectById(jobId);
            article.setCareerName(byId.getName());
        }
        return article;
    }
}




