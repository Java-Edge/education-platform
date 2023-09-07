package com.javagpt.interviewspider.entity.service.impl;

import com.alibaba.fastjson.JSON;
import com.javagpt.interviewspider.data.boss.PositionPage;
import com.javagpt.interviewspider.dto.boss.BossResult;
import com.javagpt.interviewspider.dto.boss.BossResultBody;
import com.javagpt.interviewspider.entity.service.BossRecruitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;

/**
 * @author bubaiwantong
 * @date 2023/7/30 23:08
 * @description this is a class file created by bubaiwantong in 2023/7/30 23:08
 */
@Service
@Slf4j
public class BossRecruitServiceImpl implements BossRecruitService {

    @Resource
    private RestTemplate restTemplate;


    @Override
    public void grabInfo() {
        try {
            String url = "https://www.zhipin.com/wapi/zpgeek/search/joblist.json" +
                    "?scene=1" +
                    "&query=" +
                    "&city=101010100" +
                    "&experience=" +
                    "&payType=" +
                    "&partTime=" +
                    "&degree=" +
                    "&industry=" +
                    "&scale=" +
                    "&stage=" +
                    "&position=" +
                    "&jobType=" +
                    "&salary=" +
                    "&multiBusinessDistrict=" +
                    "&multiSubway=" +
                    "&page=1" +
                    "&pageSize=30";

            // 创建一个请求头对象
            HttpHeaders httpHeaders = new HttpHeaders();
            // 设置参数
            httpHeaders.set("authority", "www.zhipin.com");
            httpHeaders.set("accept", "application/json, text/plain, */*");
            httpHeaders.set("accept-language", "zh-CN,zh;q=0.9");
            httpHeaders.set("cookie", "wd_guid=a36948bd-c8f1-491e-8e83-e4ed4e2c026a;" +
                    " historyState=state;" +
                    " _bl_uid=eglepenj8mvabI1FLdgvfLIf3gew;" +
                    " YD00951578218230%3AWM_TID=hUXBIJC4%2BNtBEVBEABbQfGPpkDCwGK%2Bz; " +
                    "lastCity=101280600; " +
                    "gdxidpyhxdE=cDhesuPIT1onDZaDm4B4SyemRj88dpdtybrRZ29HtNaaZEVmEp55vmosve%5CYDhaKcuw5GKBuZEiCKOr9ZZabCz%2F%2BMOTOMcXmjsuI8rcCiEC4ZS%2FxYE2NrLrE56qln2aqbEh%5CROQ2aNClQVHzlBpiCiTKDhg5xtaLO5THp18KsCZ1Pviz%3A1690618414048; " +
                    "YD00951578218230%3AWM_NI=VQFz4ZW1q6%2BmBNiK6P2mT8DkntMDZvq4U9NKb2xVjlD8QIUNIp1WlSKv1Eb8QG%2Fgf%2FSy%2BsssGikdP4mNdP3sqC8InV%2FJPO6E1lFZ0G2bZdoTXtKklQVwRrtz6v0hI5CcdFo%3D; " +
                    "YD00951578218230%3AWM_NIKE=9ca17ae2e6ffcda170e2e6eea5b8478786ff99ce3aaa9e8bb7c55a978f8f83c13cb1ef9f8ae75a92f5c096c52af0fea7c3b92a968f8a9bb75295efbfd7aa678292a7acb55cf2b39cd9c761aeac83a5d97eb3ec9bdacd41a2eeb8b7e96888e89faad8468bb0ffb8ef5989aaffa6e1349399e188e52591a7e594f27985b7a585d340af92acb1b279b39df8aaed5eed96a0b0b54ff3b48abbb34df2be9685ee609cac858cef5b96a99e93f772f6aa0089cc3f81bdaca7ee37e2a3; wt2=DZekMCV8PHZ0oRpZKq_cgYkoiO1FphevdUG8o35bw0c5vE6lP5LMQwCnjrGJm4JLPVJbWfeVzpSAuPf3bSERXVw~~; " +
                    "wbg=0;" +
                    " warlockjssdkcross=%7B%22distinct_id%22%3A%22115483158%22%2C%22first_id%22%3A%22189a0ab846ab4d-08c945c7ce7753-26021151-1327104-189a0ab846bbbc%22%2C%22props%22%3A%7B%7D%2C%22device_id%22%3A%22189a0ab846ab4d-08c945c7ce7753-26021151-1327104-189a0ab846bbbc%22%7D;" +
                    " __g=-;" +
                    " Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1690617500,1690640740,1690684436;" +
                    " __zp_seo_uuid__=c01cd09a-b4bd-44bf-af3b-884b93a4bfa9;" +
                    " __l=r=https%3A%2F%2Fyoule.zhipin.com%2F&l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Fjob%3Fcity%3D101010100%26page%3D2&s=3&friend_source=0&s=3&friend_source=0;" +
                    " geek_zp_token=V1QNsiFez-21tiVtRvxh0fKiuw5Tzexyo~; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1690729558;" +
                    " __zp_stoken__=2ddeeMSMIHAx4KAISPyUhKn0qXmlpb0oUMmg9CjMSJ1sZYXpIbFV5Uzc9PRwxMmcwLX9WKVwGexN%2BOg8mLi1WPCQSXUkMagklTSRGaRR7Ij4XGEdcEGV1JxAdFxoGWQQub0ZkVi1ndm82fnw%3D;" +
                    " __c=1690684434; __a=52115536.1648184705.1690640738.1690684434.378.18.96.188;" +
                    " __zp_sname__=1f49f5f3; __zp_sseed__=s31A5bfjAk2xYdpOfSyxMZc/bmzbT82QqiCfd7ZYVAU=;" +
                    " __zp_sts__=1690729462106");
            httpHeaders.set("referer", "https://www.zhipin.com/web/geek/job?city=101010100");
            httpHeaders.set("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"");
            httpHeaders.set("sec-ch-ua-mobile", "?0");
            httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
            httpHeaders.set("sec-fetch-dest", "empty");
            httpHeaders.set("sec-fetch-mode", "cors");
            httpHeaders.set("sec-fetch-site", "same-origin");
            httpHeaders.set("token", "mGlLuUS9whtv2dnh");
            httpHeaders.set("traceid", "A6373675-CC4A-46AD-B137-7F97B771E69F");
            httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
            httpHeaders.set("x-requested-with", "XMLHttpRequest");
            httpHeaders.set("zp_token", "V1QNsiFez-21tiVtRvxh0fKiuw5Tzexyo~");


            // 创建一个响应体对象
            HttpEntity<String> httpEntity = new HttpEntity(httpHeaders);

            // 发送GET请求
            ResponseEntity<BossResultBody> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, BossResultBody.class);

            BossResultBody<PositionPage> body = response.getBody();
            BossResult<PositionPage> result = JSON.parseObject(JSON.toJSONString(body.getZpData()), BossResult.class);


            log.debug(result.toString());

            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200");
            }

//            return result;
        } catch (Exception e) {
            log.error("can not grab boss data", e);
        }
//        return null;
    }
}
