package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.javagpt.interviewspider.data.ContentData;
import com.javagpt.interviewspider.data.ImageMoment;
import com.javagpt.interviewspider.data.InterviewData;
import com.javagpt.interviewspider.data.MomentData;
import com.javagpt.interviewspider.dto.*;
import com.javagpt.interviewspider.entity.CareerEntity;
import com.javagpt.interviewspider.entity.InterviewExperienceArticleEntity;
import com.javagpt.interviewspider.entity.InterviewExperienceImageEntity;
import com.javagpt.interviewspider.service.CareerService;
import com.javagpt.interviewspider.service.InterviewExperienceArticleService;
import com.javagpt.interviewspider.service.InterviewExperienceImageService;
import com.javagpt.interviewspider.service.SpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JavaGPT
 * @date 2023/7/23 0:01
 * @description this is a class file created by JavaGPT in 2023/7/23 0:01
 */
@Service
@Slf4j
public class SpiderServiceImpl implements SpiderService {

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private InterviewExperienceImageService experienceImageService;

    @Autowired
    private InterviewExperienceArticleService experienceArticleService;

    @Autowired
    private CareerService careerService;

    @Override
    public void work() {


        Page<InterviewData> response = getResponse(1);

        handleData(response);

        Integer totalPage = response.getTotalPage();
        for (int i = 2; i < totalPage; i++) {
            response = getResponse(i);
            handleData(response);
        }


    }


    /**
     * 获取数据
     *
     * @return
     */
    public Page<InterviewData> getResponse(int currentPage) {
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

            ExperienceParam experienceParam = new ExperienceParam();
            experienceParam.setCompanyList(new ArrayList<>());
            experienceParam.setJobId(11002);
            experienceParam.setLevel(3);
            experienceParam.setOrder(3);
            experienceParam.setPage(currentPage);
            experienceParam.setIsNewJob(true);


            String url = "https://gw-c.nowcoder.com/api/sparta/job-experience/experience/job/list?_=1690036293557";

            HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(experienceParam), httpHeaders);
            ResponseEntity<ResultBody> response = restTemplate.postForEntity(url, httpEntity, ResultBody.class);
            ResultBody body = response.getBody();

            Page<InterviewData> data = (Page<InterviewData>) body.getData();

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
    public void handleData(Page<InterviewData> page) {

        List<InterviewData> records = page.getRecords();

        List<InterviewExperienceArticleEntity> articleEntities = new ArrayList<>();
        List<InterviewExperienceImageEntity> imageEntities = new ArrayList<>();

        for (InterviewData record : records) {
            InterviewExperienceArticleEntity articleEntity = new InterviewExperienceArticleEntity();
            List<InterviewExperienceImageEntity> imageEntityList = new ArrayList<>();
            if (record.getContentType() == 74) {
                MomentData momentData = record.getMomentData();
                BeanUtils.copyProperties(momentData, articleEntity);
//                articleEntity.setUserId(momentData.getUserId());
                articleEntity.setCreateAt(momentData.getCreatedAt());
                List<ImageMoment> imageList = momentData.getImgMoment();

                // 保存图片
                if (imageList != null && imageList.size() > 0) {
                    for (int i = 0; i < imageList.size(); i++) {
                        ImageMoment imageMoment = imageList.get(i);
                        InterviewExperienceImageEntity imageEntity = new InterviewExperienceImageEntity();
                        BeanUtils.copyProperties(imageMoment, imageEntity);

                        imageEntity.setRecordId(momentData.getId());
                        imageEntity.setSort(i);

                        imageEntityList.add(imageEntity);
                    }
                }
            } else if (record.getContentType() == 250) {
                ContentData contentData = record.getContentData();
                BeanUtils.copyProperties(contentData, articleEntity);
                articleEntity.setUserId(contentData.getAuthorId());
                articleEntity.setCreateAt(contentData.getCreateTime());
                List<ImageMoment> imageList = contentData.getContentImageUrls();

                // 保存图片
                if (imageList != null && imageList.size() > 0) {
                    for (int i = 0; i < imageList.size(); i++) {
                        ImageMoment imageMoment = imageList.get(i);
                        InterviewExperienceImageEntity imageEntity = new InterviewExperienceImageEntity();
                        BeanUtils.copyProperties(imageMoment, imageEntity);

                        imageEntity.setRecordId(contentData.getId());
                        imageEntity.setSort(i);

                        imageEntityList.add(imageEntity);
                    }
                }
            }
            articleEntities.add(articleEntity);
            // 将每个文章的图片均存储到集合里面，统一存储
            imageEntities.addAll(imageEntityList);
        }

        // 存储到数据库中
        experienceArticleService.saveOrUpdateBatch(articleEntities);
        experienceImageService.saveOrUpdateBatch(imageEntities);

    }

    /**
     * 获取职业
     */
    @Override
    public List<CareerDTO> grabCareer() {
        try {
            String url = "https://gw-c.nowcoder.com/api/sparta/job-experience/experience/job/level3CareerJob?careerJobId=11200&_=1690270527713";
            ResponseEntity<ResultBody> response = restTemplate.getForEntity(url, ResultBody.class);
            ResultBody body = response.getBody();

            Result result = JSON.parseObject(JSON.toJSONString(body.getData()), Result.class);

            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200, response = {}", response);
            }
            boolean b = careerService.saveOrUpdateBatch(result.getResult());
/*            ArrayList<CareerEntity> careerEntities = new ArrayList<>();
            for (CareerDTO careerDTO : result.getResult()) {
                CareerEntity careerEntity = new CareerEntity();
                BeanUtils.copyProperties(careerDTO, careerEntity);
                careerEntities.add(careerEntity);
            }
            boolean b = careerService.saveOrUpdateBatch(careerEntities);*/

            return null;
        } catch (Exception e) {
            log.error("can not grab career", e);
        }
        return null;
    }
}
