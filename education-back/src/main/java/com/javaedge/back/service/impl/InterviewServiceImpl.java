package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.javaedge.back.dto.InterviewDTO;
import com.javaedge.back.entity.Career;
import com.javaedge.back.entity.InterviewEntity;
import com.javaedge.back.mapper.CareerMapper;
import com.javaedge.back.mapper.InterviewMapper;
import com.javaedge.back.service.InterviewService;
import com.javaedge.common.constant.CommonConstants;
import com.javaedge.common.req.PageQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class InterviewServiceImpl extends ServiceImpl<InterviewMapper, InterviewEntity>
    implements InterviewService {

    @Autowired
    private InterviewMapper interviewMapper;

    @Autowired
    private CareerMapper careerMapper;

    @Autowired
    private Cache<String, IPage<InterviewEntity>> interviewPageCache;

    @Autowired
    private Cache<String, InterviewEntity> interviewDetailCache;

    @Override
    public IPage<InterviewEntity> selectByCondition(PageQueryParam<InterviewDTO> pageQueryParam) {
        // 处理查询全部岗位的情况
        if (Objects.nonNull(pageQueryParam.getParam()) && Objects.nonNull(pageQueryParam.getParam().getJobId())
                // jobId=-1：查询全部岗位
                && pageQueryParam.getParam().getJobId() == CommonConstants.select_all) {
            pageQueryParam.getParam().setJobId(null);
        }

        if (pageQueryParam.getParam() == null){
            pageQueryParam.setParam(new InterviewDTO());
        }

        // 构建缓存键：pageNo-pageSize-jobId
        Integer jobId = pageQueryParam.getParam().getJobId();
        String cacheKey = String.format("%d-%d-%s",
                pageQueryParam.getPageNo(),
                pageQueryParam.getPageSize(),
                jobId != null ? jobId : "all");

        // 尝试从缓存获取
        IPage<InterviewEntity> cachedResult = interviewPageCache.getIfPresent(cacheKey);
        if (cachedResult != null) {
            return cachedResult;
        }

        // 缓存未命中，执行数据库查询
        Page<InterviewEntity> interviewEntityPage = new Page<>();
        interviewEntityPage.setSize(pageQueryParam.getPageSize());
        interviewEntityPage.setCurrent(pageQueryParam.getPageNo());

        IPage<InterviewEntity> result = interviewMapper.selectByCondition(pageQueryParam.getParam(), interviewEntityPage);

        // 将结果放入缓存
        interviewPageCache.put(cacheKey, result);

        return result;
    }

    @Override
    public InterviewEntity articleOf(String id) {
        // 尝试从缓存获取
        InterviewEntity cachedArticle = interviewDetailCache.getIfPresent(id);
        if (cachedArticle != null) {
            return cachedArticle;
        }

        // 缓存未命中，执行数据库查询
        InterviewEntity article = getById(id);
        Integer jobId = article.getJobId();
        if (Objects.nonNull(jobId)) {
            Career career = careerMapper.selectById(jobId);
            article.setCareerName(career.getName());
        }

        // 将结果放入缓存
        interviewDetailCache.put(id, article);

        return article;
    }
}