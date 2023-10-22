package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.ArticleDTO;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.Career;
import com.javagpt.back.entity.InnerRecommend;
import com.javagpt.back.entity.InterviewEntity;
import com.javagpt.back.mapper.CareerMapper;
import com.javagpt.back.mapper.InterviewMapper;
import com.javagpt.back.service.InnerRecommendService;
import com.javagpt.back.mapper.InnerRecommendMapper;
import com.javagpt.back.vo.ArticleVO;
import com.javagpt.back.vo.InnerRecommendVO;
import com.javagpt.common.req.PageQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 26314
 * @description 针对表【inner_recommend】的数据库操作Service实现
 * @createDate 2023-10-22 23:10:25
 */
@Service
public class InnerRecommendServiceImpl extends ServiceImpl<InnerRecommendMapper, InnerRecommend>
        implements InnerRecommendService {


    @Autowired
    private InnerRecommendMapper innerRecommendMapper;

    @Autowired
    private CareerMapper careerMapper;

    @Override
    public IPage<InnerRecommendVO> selectByCondition(PageQueryParam<InnerRecommendQueryDTO> pageQueryParam) {
        // 如果传入的 jobId 是 -1 表示查询全部岗位
        if (pageQueryParam.getParam() != null && pageQueryParam.getParam().getJobId() != null && pageQueryParam.getParam().getJobId() == -1) {
            pageQueryParam.getParam().setJobId(null);
        }

        Page<InnerRecommendVO> page = new Page<>();
        page.setSize(pageQueryParam.getPageSize());
        page.setCurrent(pageQueryParam.getPageNo());
        if (pageQueryParam.getParam() == null) {
            pageQueryParam.setParam(new InnerRecommendQueryDTO());
        }
        IPage<InnerRecommendVO> articleVOS = innerRecommendMapper.selectByCondition(page, pageQueryParam.getParam());
        return articleVOS;
    }

    @Override
    public InnerRecommend articleOf(String id) {
        InnerRecommend article = getById(id);
        Integer jobId = article.getJobId();
        if (jobId != null) {
            Career byId = careerMapper.selectById(jobId);
            article.setCareerName(byId.getName());
        }
        return article;
    }

}




