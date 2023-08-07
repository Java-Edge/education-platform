package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.javagpt.interviewspider.data.boss.BossContentInfo;
import com.javagpt.interviewspider.dto.boss.BossResult;
import com.javagpt.interviewspider.dto.boss.BossResultBody;
import com.javagpt.interviewspider.dto.nowcoder.Result;
import com.javagpt.interviewspider.entity.ArticleEntity;
import com.javagpt.interviewspider.service.BossQuestionService;
import com.javagpt.interviewspider.service.ArticleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author bubaiwantong
 * @date 2023/7/30 14:48
 * @description this is a class file created by bubaiwantong in 2023/7/30 14:48
 */
@Service
@Slf4j
public class BossQuestionServiceImpl implements BossQuestionService {

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private ArticleService articleService;

    @Override
    public List<Object> grabQuestions() {
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
            String url = "https://youle.zhipin.com/wapi/moment/pc/question/wait2Answer/tab?noFilterPosition=0&pageSize=10&positionsLevelTwo=100100&isBossQuestion=0&isStudentQuestion=0&encryptTagId=8f00b204e9800998&listType=0&page=1&isNew=0";

            // 创建一个请求头对象
            HttpHeaders httpHeaders = new HttpHeaders();
            // 设置参数
            httpHeaders.set("authority", "youle.zhipin.com");
            httpHeaders.set("accept", "application/json, text/plain, */*");
            httpHeaders.set("accept-language", "zh-CN,zh;q=0.9");
            httpHeaders.set("cookie", "lastCity=101280600; wt2=DZekMCV8PHZ0oRpZKq_cgYkoiO1FphevdUG8o35bw0c5vE6lP5LMQwCnjrGJm4JLPVJbWfeVzpSAuPf3bSERXVw~~; wbg=0; warlockjssdkcross=%7B%22distinct_id%22%3A%22115483158%22%2C%22first_id%22%3A%22189a0ab846ab4d-08c945c7ce7753-26021151-1327104-189a0ab846bbbc%22%2C%22props%22%3A%7B%7D%2C%22device_id%22%3A%22189a0ab846ab4d-08c945c7ce7753-26021151-1327104-189a0ab846bbbc%22%7D; wd_guid=352ecf69-9c03-4662-a025-e05338529328; historyState=state; __g=-; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1690617500,1690640740,1690684436; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1690684436; __zp_stoken__=2ddeeMWoAGnwQSWltUSl%2FLgt%2FV346HHl6Ym8teSwSNGJNSXtCbB9GV206Fm1aKlBLLX9WKVxuBhh0bHkmIldhIkxnDwISYixwQR5eaRR7Ij4XGDxrCA4EDBdHEyVMWQQub0ZkVi1ndm82fnw%3D; Hm_lvt_f97992073bffedfa462561a24c99eb83=1690617741,1690640744,1690684440; __zp_seo_uuid__=fefe726d-b2e5-4a0d-9a52-139e3bae6b0d; __l=r=https%3A%2F%2Fyoule.zhipin.com%2F&l=%2Fyoule.zhipin.com%2Farticles%2F&s=1&s=3&friend_source=0; Hm_lpvt_f97992073bffedfa462561a24c99eb83=1690699091; __c=1690684434; __a=52115536.1648184705.1690640738.1690684434.355.18.73.165");
            httpHeaders.set("referer", "https://youle.zhipin.com/questions/c_100100/");
            httpHeaders.set("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"");
            httpHeaders.set("sec-ch-ua-mobile", "?0");
            httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
            httpHeaders.set("sec-fetch-dest", "empty");
            httpHeaders.set("sec-fetch-mode", "cors");
            httpHeaders.set("sec-fetch-site", "same-origin");
            httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
            httpHeaders.set("x-requested-with", "XMLHttpRequest");

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
