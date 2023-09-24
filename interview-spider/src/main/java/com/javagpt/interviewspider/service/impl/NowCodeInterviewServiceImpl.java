package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.javagpt.interviewspider.data.nowcoder.ContentData;
import com.javagpt.interviewspider.data.nowcoder.ImageMoment;
import com.javagpt.interviewspider.data.nowcoder.InterviewData;
import com.javagpt.interviewspider.data.nowcoder.MomentData;
import com.javagpt.interviewspider.entity.ArticleEntity;
import com.javagpt.interviewspider.service.ArticleService;
import com.javagpt.interviewspider.param.ExperienceParam;
import com.javagpt.interviewspider.dto.nowcoder.Page;
import com.javagpt.interviewspider.dto.nowcoder.ResultBody;
import com.javagpt.interviewspider.entity.CareerEntity;
import com.javagpt.interviewspider.service.CareerService;
import com.javagpt.interviewspider.service.SpiderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@Service
@Slf4j
public class NowCodeInterviewServiceImpl implements SpiderService {

    @Resource
    private RestTemplate restTemplate;


    @Autowired
    private ArticleService articleService;

    @Autowired
    private CareerService careerService;

    @Override
    public void obtainInterviewExperience() {
        // 从数据库中查询每个职位，并且到牛客网上遍历面经信息
        LambdaQueryWrapper<CareerEntity> queryWrapper = new LambdaQueryWrapper<CareerEntity>().eq(CareerEntity::getIsGrab, 1);
        List<CareerEntity> list = careerService.list(queryWrapper);

        for (CareerEntity careerEntity : list) {
            System.out.println(careerEntity.getId());
            ExperienceParam experienceParam = new ExperienceParam();
            experienceParam.setJobId(careerEntity.getId());
            experienceParam.setPage(1);
            experienceParam.setLevel(experienceParam.getLevel());
            saveInterviewByJobId(experienceParam);
        }
    }


    /**
     * 根据职业获取这个职业的全部面经
     *
     * @param experienceParam
     */
    public void saveInterviewByJobId(ExperienceParam experienceParam) {
        Page<InterviewData> response = getResponse(experienceParam);
        handleData(experienceParam.getJobId(),response);
        Integer totalPage = response.getTotalPage();
        for (int i = 2; i < totalPage; i++) {
            experienceParam.setPage(i);
            response = getResponse(experienceParam);
            handleData(experienceParam.getJobId(),response);
        }

        log.debug( "============"+ experienceParam.getJobId() + "岗位一共存储"    + response.getTotal() +  "条数据============");
    }


    /**
     * 获取数据
     *
     * @return
     */
    public Page<InterviewData> getResponse(ExperienceParam experienceParam) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(org.springframework.http.MediaType.parseMediaType("application/json;charset=UTF-8"));
            httpHeaders.set("authority", "gw-c.nowcoder.com");
            httpHeaders.set("accept", "application/json, text/plain, */*");
            httpHeaders.set("accept-language", "zh-CN,zh;q=0.9");
            httpHeaders.set("content-type", "application/json");
            httpHeaders.set("cookie", "NOWCODERCLINETID=A43E646B96AB496A5521ADF4AD4AFA30; gr_user_id=1a9e996f-8493-46e9-bddd-91ce00a9d766; c196c3667d214851b11233f5c17f99d5_gr_last_sent_cs1=675723764; NOWCODERUID=4F39CF16DA0A1C34E715914260FE808F; c196c3667d214851b11233f5c17f99d5_gr_session_id=f17f4c11-3ae3-4710-82aa-fee9d29ab4c6; Hm_lvt_a808a1326b6c06c437de769d1b85b870=1688303892,1690034135; isAgreementChecked=true; t=A0D0E277D4DD2ECC80979AE78178A2B7; c196c3667d214851b11233f5c17f99d5_gr_last_sent_sid_with_cs1=f17f4c11-3ae3-4710-82aa-fee9d29ab4c6; c196c3667d214851b11233f5c17f99d5_gr_session_id_f17f4c11-3ae3-4710-82aa-fee9d29ab4c6=true; acw_tc=ac2853ffea6bf2588a3b3986b56520d89b1e4cc1e799b72516154742653e186d; c196c3667d214851b11233f5c17f99d5_gr_cs1=675723764; Hm_lpvt_a808a1326b6c06c437de769d1b85b870=1690036290");
            httpHeaders.set("origin", "https://www.nowcoder.com");
            httpHeaders.set("referer", "https://www.nowcoder.com/");
            httpHeaders.set("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"");
            httpHeaders.set("sec-ch-ua-mobile", "?0");
            httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
            httpHeaders.set("sec-fetch-dest", "empty");
            httpHeaders.set("sec-fetch-mode", "cors");
            httpHeaders.set("sec-fetch-site", "same-site");
            httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");

            String param = "{\n" + "    \"companyList\": [],\n" + "    \"jobId\": 11002,\n" + "    \"level\": 3,\n" + "    \"order\": 3,\n" + "    \"page\": 1,\n" + "    \"isNewJob\": true\n" + "}";

            experienceParam.setCompanyList(new ArrayList<>());
            experienceParam.setLevel(3);
            experienceParam.setOrder(3);
            experienceParam.setIsNewJob(true);


            String url = "https://gw-c.nowcoder.com/api/sparta/job-experience/experience/job/list?_=1690036293557";

            HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(experienceParam), httpHeaders);
            ResponseEntity<ResultBody> response = restTemplate.postForEntity(url, httpEntity, ResultBody.class);
            ResultBody body = response.getBody();

            Page<InterviewData> data = JSON.parseObject(JSON.toJSONString(body.getData()), Page.class);

            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200, response = {}", response);
            }

            return data;

        } catch (Exception e) {
            log.error("update on es  exception", e);
        }
        return null;
    }


    /**
     * 处理数据
     *
     * @param page
     */
    @Transactional(rollbackFor = Exception.class)
    public void handleData(int jobId,Page<InterviewData> page) {

        List<InterviewData> records = JSON.parseArray(JSON.toJSONString(page.getRecords()), InterviewData.class);

        List<ArticleEntity> articleEntities = new ArrayList<>();

        for (InterviewData record : records) {
            ArticleEntity articleEntity = new ArticleEntity();
            if (record.getContentType() == 74) {
                MomentData momentData = record.getMomentData();
                BeanUtils.copyProperties(momentData, articleEntity);
//                articleEntity.setUserId(momentData.getUserId());
                articleEntity.setCreateAt(momentData.getCreatedAt());
                articleEntity.setContentType(record.getContentType().intValue());
                articleEntity.setId(UUID.randomUUID().toString());
                articleEntity.setSourceId(momentData.getId());
                List<ImageMoment> imageList = momentData.getImgMoment();
            } else if (record.getContentType() == 250) {
                ContentData contentData = record.getContentData();
                BeanUtils.copyProperties(contentData, articleEntity);
                articleEntity.setUserId(contentData.getAuthorId());
                articleEntity.setCreateAt(contentData.getCreateTime());
                articleEntity.setContentType(record.getContentType().intValue());
                articleEntity.setId(UUID.randomUUID().toString());
                articleEntity.setSourceId(contentData.getId());
                List<ImageMoment> imageList = contentData.getContentImageUrls();
            }
            // 文章类别 1代表面经
            articleEntity.setArticleType(1);
            articleEntity.setId(null);
            articleEntity.setJobId(jobId);
            articleEntities.add(articleEntity);
        }

        // 存储到数据库中
        articleService.saveOrUpdateBatch(articleEntities);

    }


}