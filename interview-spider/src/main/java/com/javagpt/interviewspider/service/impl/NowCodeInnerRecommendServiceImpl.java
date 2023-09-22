package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.javagpt.interviewspider.data.nowcoder.ContentData;
import com.javagpt.interviewspider.data.nowcoder.InterviewData;
import com.javagpt.interviewspider.data.nowcoder.MomentData;
import com.javagpt.interviewspider.dto.nowcoder.Page;
import com.javagpt.interviewspider.dto.nowcoder.ResultBody;
import com.javagpt.interviewspider.entity.ArticleEntity;
import com.javagpt.interviewspider.service.ArticleService;
import com.javagpt.interviewspider.service.NowCodeInnerRecommendService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class NowCodeInnerRecommendServiceImpl implements NowCodeInnerRecommendService {

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private ArticleService articleService;

    @Override
    public void grabInnerRecommend() {
        for (int i = 1; i <= 20; i++) {
            Page<InterviewData> response = getResponse(i);
            handleData(response);
        }
    }

    /**
     * 获取数据
     *
     * @return
     */
    public Page<InterviewData> getResponse(int pageNo) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(org.springframework.http.MediaType.parseMediaType("application/json;charset=UTF-8"));
            httpHeaders.set("authority", "gw-c.nowcoder.com");
            httpHeaders.set("accept", "application/json, text/plain, */*");
            httpHeaders.set("accept-language", "zh-CN,zh;q=0.9");
            httpHeaders.set("content-type", "application/json");
            httpHeaders.set("cookie", "gr_user_id=0b50e281-1e21-49f8-951b-518afc5b205f; NOWCODERCLINETID=1095627B332EB40A2FF948469344344E; NOWCODERUID=65D603D59DBCAD1E8B8ABDA41BE8CA08; isAgreementChecked=true; c196c3667d214851b11233f5c17f99d5_gr_last_sent_cs1=675723764; _clck=e9dx8s|2|fep|0|1341; acw_tc=6ddd82b46f77f4e8a5e40c3aacd66ad07b42b1a096ce230cfeb0edd23ebc1d19; c196c3667d214851b11233f5c17f99d5_gr_session_id=85c88c0d-4565-49c4-9183-7d5139d4acb4; Hm_lvt_a808a1326b6c06c437de769d1b85b870=1694533368,1694870327,1695133053,1695306471; t=FA3138F933969AB950ADF4637A29254F; c196c3667d214851b11233f5c17f99d5_gr_last_sent_sid_with_cs1=85c88c0d-4565-49c4-9183-7d5139d4acb4; c196c3667d214851b11233f5c17f99d5_gr_session_id_85c88c0d-4565-49c4-9183-7d5139d4acb4=true; c196c3667d214851b11233f5c17f99d5_gr_cs1=675723764; Hm_lpvt_a808a1326b6c06c437de769d1b85b870=1695306527");
            httpHeaders.set("origin", "https://www.nowcoder.com");
            httpHeaders.set("referer", "https://www.nowcoder.com/");
            httpHeaders.set("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"");
            httpHeaders.set("sec-ch-ua-mobile", "?0");
            httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
            httpHeaders.set("sec-fetch-dest", "empty");
            httpHeaders.set("sec-fetch-mode", "cors");
            httpHeaders.set("sec-fetch-site", "same-site");
            httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");

            String url = "https://gw-c.nowcoder.com/api/sparta/home/tab/content?pageNo=" + pageNo + "&categoryType=1&tabId=861&_=1695306542751";

            HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
            ResponseEntity<ResultBody> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, ResultBody.class);
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
    public void handleData(Page<InterviewData> page) {

        List<InterviewData> records = JSON.parseArray(JSON.toJSONString(page.getRecords()), InterviewData.class);

        List<ArticleEntity> articleEntities = new ArrayList<>();

        for (InterviewData record : records) {
            ArticleEntity articleEntity = new ArticleEntity();
            if (record.getContentType() == 74) {
                MomentData momentData = record.getMomentData();
                BeanUtils.copyProperties(momentData, articleEntity);
                articleEntity.setCreateAt(momentData.getCreatedAt());
                articleEntity.setContentType(record.getContentType().intValue());
                articleEntity.setId(UUID.randomUUID().toString());
                articleEntity.setSourceId(momentData.getId());
            } else if (record.getContentType() == 250) {
                ContentData contentData = record.getContentData();
                BeanUtils.copyProperties(contentData, articleEntity);
                articleEntity.setUserId(contentData.getAuthorId());
                articleEntity.setCreateAt(contentData.getCreateTime());
                articleEntity.setContentType(record.getContentType().intValue());
                articleEntity.setId(UUID.randomUUID().toString());
                articleEntity.setSourceId(contentData.getId());
            }
            // 文章类型 2 代表内推信息
            articleEntity.setArticleType(2);
            articleEntity.setId(null);
            articleEntities.add(articleEntity);
        }

        // 存储到数据库中
        articleService.saveOrUpdateBatch(articleEntities);

    }

}
