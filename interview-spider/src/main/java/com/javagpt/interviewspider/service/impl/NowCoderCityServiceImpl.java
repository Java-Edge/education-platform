package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.javagpt.interviewspider.data.nowcoder.JobData;
import com.javagpt.interviewspider.dto.nowcoder.Result;
import com.javagpt.interviewspider.dto.nowcoder.ResultBody;
import com.javagpt.interviewspider.service.NowCoderCityService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 *
 * @Author JavaGPT
 * @Date 2023/8/1 14:23
 * @PackageName:com.javagpt.interviewspider.service.impl
 * @ClassName: NowCoderCityServiceImpl
 * @Version 1.0
 */
@Service
@Slf4j
public class NowCoderCityServiceImpl implements NowCoderCityService {

    @Resource
    private RestTemplate restTemplate;
    @Override
    public void grabCities() {

    }


    /**
     * 留个位置，后面直接拿牛客网的城市
     * @return
     */
    private List<JobData> getResponse() {
        try {
            String url = "https://api-cdn.nowcoder.com/nccommon/city/all";
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
