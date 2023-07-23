package com.javagpt.interviewspider.service.impl;

import com.javagpt.interviewspider.service.SpiderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author bubaiwantong
 * @date 2023/7/22 23:54
 * @description this is a class file created by bubaiwantong in 2023/7/22 23:54
 */
@Service
@Slf4j
public class SpiderServiceImpl implements SpiderService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void work() {
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

            String param = "{\n" +
                    "    \"companyList\": [],\n" +
                    "    \"jobId\": 11002,\n" +
                    "    \"level\": 3,\n" +
                    "    \"order\": 3,\n" +
                    "    \"page\": 1,\n" +
                    "    \"isNewJob\": true\n" +
                    "}";


            String url = "https://gw-c.nowcoder.com/api/sparta/job-experience/experience/job/list?_=1690036293557";

            HttpEntity<String> httpEntity = new HttpEntity<>(param, httpHeaders);
            ResponseEntity<Object> response = restTemplate.postForEntity(url, httpEntity, Object.class);
            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200, response = {}", response);
            }
        } catch (Exception e) {
            log.error("update on es  exception", e);
        }
    }
}
