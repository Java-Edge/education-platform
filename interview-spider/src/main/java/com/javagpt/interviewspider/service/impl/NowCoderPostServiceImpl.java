package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.interviewspider.data.nowcoder.JobData;
import com.javagpt.interviewspider.data.nowcoder.JobInfo;
import com.javagpt.interviewspider.dto.nowcoder.Result;
import com.javagpt.interviewspider.dto.nowcoder.ResultBody;
import com.javagpt.interviewspider.entity.JobEntity;
import com.javagpt.interviewspider.mapper.JobMapper;
import com.javagpt.interviewspider.service.NowCoderPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author JavaGPT
 * @Date 2023/8/1 13:19
 * @PackageName:com.javagpt.interviewspider.service.impl
 * @ClassName: NowCoderPostServiceImpl
 * @Version 1.0
 */
@Service
@Slf4j
public class NowCoderPostServiceImpl extends ServiceImpl<JobMapper, JobEntity>
        implements NowCoderPostService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void grabPositions() {
        List<JobData> list = getResponse();
        List<JobEntity> jobEntities = handleResult(list);

        saveOrUpdateBatch(jobEntities);
        log.debug("一共有"  +  jobEntities.size() + "职位");

    }


    private List<JobEntity> handleResult(List<JobData> list) {
        List<JobEntity> jobEntities = new ArrayList<>();
        for (JobData jobData : list) {
            // 处理当前节点职位信息
            JobEntity jobEntity = new JobEntity();
            JobInfo jobInfo = jobData.getJobInfo();
            BeanUtils.copyProperties(jobInfo, jobEntity);
            jobEntities.add(jobEntity);
            for (JobData subJob : jobData.getSubJobs()) {

                // 处理儿子节点职位信息
                JobEntity subJobEntity = new JobEntity();
                BeanUtils.copyProperties(subJob.getJobInfo(), subJobEntity);
                jobEntities.add(subJobEntity);

                // 如果还有孙子，则采用递归方式处理孙子职位信息
                if (subJob.getSubJobs() != null && subJob.getSubJobs().size() > 0){
                    List<JobEntity> subEntities = handleResult(subJob.getSubJobs());
                    jobEntities.addAll(subEntities);
                }

            }
        }
        return jobEntities;
    }


    private List<JobData> getResponse() {
        try {
            String url = "https://www.nowcoder.com/completeness/all-career-jobs";
            ResponseEntity<ResultBody> response = restTemplate.getForEntity(url, ResultBody.class);
            ResultBody<Result> body = response.getBody();
            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200");
            }
            Result<JobData> result = JSON.parseObject(JSON.toJSONString(body.getData()), Result.class);

            List<JobData> list = JSON.parseArray(JSON.toJSONString(result.getAllJobs()), JobData.class);

            return list;
        } catch (Exception e) {
            log.error("can not grab career", e);
        }
        return null;
    }


}
