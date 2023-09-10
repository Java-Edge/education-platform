package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.javagpt.interviewspider.data.boss.BossContentInfo;
import com.javagpt.interviewspider.dto.boss.BossResult;
import com.javagpt.interviewspider.dto.boss.BossResultBody;
import com.javagpt.interviewspider.dto.nowcoder.Result;
import com.javagpt.interviewspider.entity.ArticleEntity;
import com.javagpt.interviewspider.service.ArticleService;
import com.javagpt.interviewspider.service.BossService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/29 23:24
 * @description this is a class file created by bubaiwantong in 2023/7/29 23:24
 */
@Service
@Slf4j
public class BossServiceImpl implements BossService {


    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private ArticleService articleService;


    @Override
    public List<BossContentInfo> grabInterviewExperience() {
        int startPage = 1;
        int endPage  = 2;
        for (int currentPage = startPage; currentPage < endPage; currentPage++) {
            List<BossContentInfo> list = getResponse(currentPage);
            handleData(list);
        }
        return null;
    }

    /**
     * 根据页码获取当前页面的面试经验信息
     *
     * @param currentPage
     * @return
     */
    public List<BossContentInfo> getResponse(int currentPage) {
        try {
            String url = "https://youle.zhipin.com/wapi/moment/pc/discover/feedList?page=1";

            // 创建一个请求头对象
            HttpHeaders httpHeaders = new HttpHeaders();
            // 设置参数
            httpHeaders.set("authority", "youle.zhipin.com");
            httpHeaders.set("accept", "application/json, text/plain, */*");
            httpHeaders.set("accept-language", "zh-CN,zh;q=0.9");
            httpHeaders.set("cookie", "lastCity=101280600; wt2=DZekMCV8PHZ0oRpZKq_cgYkoiO1FphevdUG8o35bw0c5vE6lP5LMQwCnjrGJm4JLPVJbWfeVzpSAuPf3bSERXVw~~; wbg=0; wljssdk_cross_new_user=1; warlockjssdkcross=%7B%22distinct_id%22%3A%22115483158%22%2C%22first_id%22%3A%22189a0ab846ab4d-08c945c7ce7753-26021151-1327104-189a0ab846bbbc%22%2C%22props%22%3A%7B%7D%2C%22device_id%22%3A%22189a0ab846ab4d-08c945c7ce7753-26021151-1327104-189a0ab846bbbc%22%7D; __g=-; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1690617500,1690640740; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1690640740; __zp_stoken__=09f2eOD1HZ1lbO24SJkADLW57LEElAGdvbSEiPkA0XjUjSA9cTksDSW8kHiNSTAR8D3sSIE5uGEk3byZnOzUMBQAKUDQUTWcJKkZYKAFDY0A7HFMZJBMaIwBMKjANMSsyTUIgXz9nZ0BgIXQ%3D; Hm_lvt_f97992073bffedfa462561a24c99eb83=1690617741,1690640744; __zp_seo_uuid__=691a525f-1f60-42fe-ab46-9bba492111c2; wd_guid=352ecf69-9c03-4662-a025-e05338529328; historyState=state; Hm_lpvt_f97992073bffedfa462561a24c99eb83=1690640811; __c=1690640738; __l=r=https%3A%2F%2Fyoule.zhipin.com%2Fquestions%2F&l=%2Fyoule.zhipin.com%2Frecommend%2Fselected%2F&s=3&friend_source=0&s=3&friend_source=0; __a=52115536.1648184705.1690617499.1690640738.214.17.8.24");
            httpHeaders.set("referer", "https://youle.zhipin.com/recommend/selected/");
            httpHeaders.set("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"");
            httpHeaders.set("sec-ch-ua-mobile", "?0");
            httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
            httpHeaders.set("sec-fetch-dest", "empty");
            httpHeaders.set("sec-fetch-mode", "cors");
            httpHeaders.set("sec-fetch-site", "same-origin");
            httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
            httpHeaders.set("x-requested-with", "XMLHttpRequest");
            httpHeaders.set("zp_token", "V1QNsiFez-21tiVtRvxh0eLSqz6TjUwSU~");

            // 创建一个响应体对象
            HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);

            // 发送GET请求
            ResponseEntity<BossResultBody> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, BossResultBody.class);
            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200");
            }

            BossResultBody<Result> body = response.getBody();

            BossResult<BossContentInfo> result = JSON.parseObject(JSON.toJSONString(body.getZpData()), BossResult.class);

            List<BossContentInfo> list = JSON.parseArray(JSON.toJSONString(result.getList()), BossContentInfo.class);

            log.debug(list.toString());

            return list;
        } catch (Exception e) {
            log.error("can not grab boss data", e);
        }
        return null;
    }


    /**
     * 处理Boss直聘上爬取的信息，整合成Entity
     *
     * @param list
     */
    private void handleData(List<BossContentInfo> list) {

        List<ArticleEntity> articleEntities = new ArrayList<>();
        for (BossContentInfo bossContentInfo : list) {
            ArticleEntity articleEntity = new ArticleEntity();

            articleEntity.setContent(bossContentInfo.getContent());
            articleEntity.setNewContent(bossContentInfo.getContentDescBackup());
            articleEntity.setCreateAt(new Date(bossContentInfo.getAddTime()));
            articleEntity.setTitle(bossContentInfo.getQuestionTitle());
            articleEntities.add(articleEntity);
        }

        log.debug(articleEntities.toString());
    }

}
