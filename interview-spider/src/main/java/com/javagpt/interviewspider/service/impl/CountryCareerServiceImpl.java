package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.javagpt.interviewspider.data.boss.PositionPage;
import com.javagpt.interviewspider.dto.CountryCareer.CompanyInfo;
import com.javagpt.interviewspider.dto.CountryCareer.CountryCareerDTO;
import com.javagpt.interviewspider.dto.CountryCareer.CountryCareerPage;
import com.javagpt.interviewspider.dto.CountryCareer.CountryCareerResultBody;
import com.javagpt.interviewspider.dto.boss.BossResult;
import com.javagpt.interviewspider.dto.boss.BossResultBody;
import com.javagpt.interviewspider.entity.CompanyEntity;
import com.javagpt.interviewspider.entity.RecruitEntity;
import com.javagpt.interviewspider.param.CountryCareerParam;
import com.javagpt.interviewspider.service.CompanyService;
import com.javagpt.interviewspider.service.CountryCareerService;
import com.javagpt.interviewspider.service.RecruitService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CountryCareerServiceImpl implements CountryCareerService {


    @Autowired
    private RecruitService recruitService;


    @Autowired
    private CompanyService companyService;

    @Value("https://gp-api.iguopin.com/api/jobs/v1/list")
    private String careerApi;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public void grabCareer() {
        List<CountryCareerDTO> countryCareers = getResponse(1, 2);
        handleResult(countryCareers);
    }


    public List<CountryCareerDTO> getResponse(int startPage, int endPage) {

        // 创建一个请求头对象
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置参数
        httpHeaders.set("authority", "gp-api.iguopin.com");
        httpHeaders.set("accept", "application/json, text/plain, */*");
        httpHeaders.set("accept-language", "zh-CN,zh;q=0.9");
        httpHeaders.set("content-type", "application/json");
        httpHeaders.set("device", "pc");
        httpHeaders.set("origin", "https://ceec.iguopin.com");
        httpHeaders.set("referer", "https://ceec.iguopin.com/");
        httpHeaders.set("sec-ch-ua", "\"Chromium\";v=\"116\", \"Not)A;Brand\";v=\"24\", \"Google Chrome\";v=\"116\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "cors");
        httpHeaders.set("sec-fetch-site", "same-site");
        httpHeaders.set("subsite", "iguopin");
        httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36");

        CountryCareerParam countryCareerParam = new CountryCareerParam();
        countryCareerParam.setPage(startPage);
        countryCareerParam.setPageSize(20);
        countryCareerParam.setSource("s_job_list");
        String projectId = "55995671404085318";
        countryCareerParam.setProjectId(Collections.singletonList(projectId));

        // 创建一个响应体对象
        HttpEntity<String> httpEntity = new HttpEntity<>(JSON.toJSONString(countryCareerParam), httpHeaders);

        // 发送POST请求
        ResponseEntity<CountryCareerResultBody> response = restTemplate.postForEntity(careerApi, httpEntity, CountryCareerResultBody.class);

        CountryCareerResultBody<CountryCareerPage> body = response.getBody();
        CountryCareerPage countryCareerPage = JSON.parseObject(JSON.toJSONString(body.getData()), CountryCareerPage.class);

        List<CountryCareerDTO> list = countryCareerPage.getList();

        return list;
    }

    public void handleResult(List<CountryCareerDTO> list) {
        if (list == null && list.size() <= 0) {
            return;
        }

        List<RecruitEntity> recruitEntities = new ArrayList<>();
        ArrayList<CompanyEntity> companyEntities = new ArrayList<>();
        for (CountryCareerDTO countryCareerDTO : list) {
            RecruitEntity recruitEntity = new RecruitEntity();
//            recruitEntity.setId(Long.valueOf(countryCareerDTO.getJobId()));
            recruitEntity.setTitle(countryCareerDTO.getJobName());
            recruitEntity.setContent(countryCareerDTO.getContents());
//            recruitEntity.setType();
//            recruitEntity.setDes();
//            recruitEntity.setPhone();
//            recruitEntity.setRecruitType();
            recruitEntity.setCompanyId(countryCareerDTO.getCompanyId());
            recruitEntity.setJobName(countryCareerDTO.getJobName());
//            recruitEntity.setExt();
            List<String> cities = countryCareerDTO.getDistrictList().stream().map(item -> item.getAreaCn()).collect(Collectors.toList());
            recruitEntity.setJobCity(StringUtils.join(cities,","));
//            recruitEntity.setJobAddress(countryCareerDTO.get);

            recruitEntity.setCreateTime(countryCareerDTO.getCreateTime());
            recruitEntity.setGraduationYear(countryCareerDTO.getEducation());
//            recruitEntity.setCareerJobId(countryCareerDTO.getJobId());
            recruitEntity.setCareerJobName(countryCareerDTO.getJobName());
//            recruitEntity.setGraduationYear();
            recruitEntity.setDeliverBegin(countryCareerDTO.getStartTime());
            recruitEntity.setDeliverEnd(countryCareerDTO.getEndTime());
            recruitEntity.setRefeshTime(countryCareerDTO.getRefreshTime());
//            recruitEntity.setFeedBackDays(countryCareerDTO);
//            recruitEntity.setDurationDays(countryCareerDTO.getD);
//            recruitEntity.setSalaryType(countryCareerDTO.getWageUnit());
            if ("11jNfoC".equals(countryCareerDTO.getWageUnit())) {
                recruitEntity.setSalaryType(2);
                recruitEntity.setSalaryMin(Math.toIntExact(countryCareerDTO.getMinWage()) / 1000);
                recruitEntity.setSalaryMax(Math.toIntExact(countryCareerDTO.getMaxWage()) / 1000);
            }

            if ("116VSUN1".equals(countryCareerDTO.getEducation())) {
                recruitEntity.setEduLevel(6000);
            } else if ("116yhC4D".equals(countryCareerDTO.getEducation())) {
                recruitEntity.setEduLevel(5000);
            }
            recruitEntities.add(recruitEntity);


            CompanyEntity companyEntity = new CompanyEntity();
            CompanyInfo companyInfo = countryCareerDTO.getCompanyInfo();
            companyEntity.setCompanyName(companyInfo.getName());
            companyEntity.setCompanyShortName(companyInfo.getShowName());
            companyEntity.setPersonScales(companyInfo.getScaleCn());
            companyEntity.setPicUrl(companyInfo.getShowLogo());
            companyEntities.add(companyEntity);
        }

        recruitService.saveOrUpdateBatch(recruitEntities);
        companyService.saveOrUpdateBatch(companyEntities);

    }


}
