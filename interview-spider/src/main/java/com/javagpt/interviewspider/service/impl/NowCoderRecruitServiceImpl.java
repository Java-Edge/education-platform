package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.javagpt.interviewspider.data.nowcoder.RecommendInternCompany;
import com.javagpt.interviewspider.data.nowcoder.RecruitData;
import com.javagpt.interviewspider.dto.nowcoder.NowCoderPage;
import com.javagpt.interviewspider.dto.nowcoder.ResultBody;
import com.javagpt.interviewspider.entity.CompanyEntity;
import com.javagpt.interviewspider.entity.RecruitEntity;
import com.javagpt.interviewspider.param.RecruitParam;
import com.javagpt.interviewspider.service.CompanyService;
import com.javagpt.interviewspider.service.NowCoderRecruitService;
import com.javagpt.interviewspider.service.RecruitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author JavaGPT
 * @Date 2023/8/1 14:48
 * @PackageName:com.javagpt.interviewspider.service.impl
 * @ClassName: NowCoderRecruitServiceImpl
 * @Version 1.0
 */
@Service
@Slf4j
public class NowCoderRecruitServiceImpl implements NowCoderRecruitService {
    @Resource
    private RestTemplate restTemplate;

    @Autowired
    private RecruitService recruitService;

    @Autowired
    private CompanyService companyService;

    @Override
    public void grabRecruits() {
        List<RecruitData> list = getResponse();
        handleResult(list);
    }

    /**
     * 获取数据
     *
     * @return
     */
    private List<RecruitData> getResponse() {

        String url = "https://www.nowcoder.com/np-api/u/job/search";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded"));
        httpHeaders.set("authority", "www.nowcoder.com");
        httpHeaders.set("accept", "application/json, text/plain, */*");
        httpHeaders.set("accept-language", "zh-CN,zh;q=0.9");
        httpHeaders.set("content-type", "application/x-www-form-urlencoded");
        httpHeaders.set("cookie", "gr_user_id=ebb5a2ba-7dcb-4e8d-8ae1-6f33ed7dcf95; NOWCODERCLINETID=7C0BF16CD0F23DBB37E0873852CCE5B2; NOWCODERUID=37A176E36523BA951A59078A11617D44; csrfToken=3yPQUj0pfspO7hNqVPtkrLgb; Hm_lvt_a808a1326b6c06c437de769d1b85b870=1689138478,1689313498,1689758270; isAgreementChecked=true; c196c3667d214851b11233f5c17f99d5_gr_last_sent_cs1=675723764; username=%E4%B8%8D%E8%B4%A5%E9%A1%BD%E7%AB%A51%E5%8F%B7; username.sig=68c61Sm_zP4YXNYjuoi8TV2j9bzAT31eFtskRDdbbD8; uid=675723764; uid.sig=Tcd1XXYCRT1lgjghZEUMfHWT7nnsf9R6H6csSmLx2wE; NCOURSEFROM=202xuexi01; _clck=1xnpuhu|2|fdm|0|1302; t=E0CC739442A0AE71F1DD3D9550636F1A; acw_tc=5d2c48f598893d54db1aa981dd3c69949a4901ae4bb491b4cd93718ba3e64921; c196c3667d214851b11233f5c17f99d5_gr_session_id=4c80a42e-7585-4c2b-89d7-8ff1a437645d; c196c3667d214851b11233f5c17f99d5_gr_last_sent_sid_with_cs1=4c80a42e-7585-4c2b-89d7-8ff1a437645d; c196c3667d214851b11233f5c17f99d5_gr_session_id_4c80a42e-7585-4c2b-89d7-8ff1a437645d=true; SERVERID=b3db3873a37b72ffe3215e70047ce355|1690871549|1690871443; SERVERCORSID=b3db3873a37b72ffe3215e70047ce355|1690871549|1690871443; Hm_lpvt_a808a1326b6c06c437de769d1b85b870=1690871551; c196c3667d214851b11233f5c17f99d5_gr_cs1=675723764");
        httpHeaders.set("origin", "https://www.nowcoder.com");
        httpHeaders.set("referer", "https://www.nowcoder.com/jobs/intern/center?recruitType=2&page=2");
        httpHeaders.set("sec-ch-ua", "\"Not/A)Brand\";v=\"99\", \"Google Chrome\";v=\"115\", \"Chromium\";v=\"115\"");
        httpHeaders.set("sec-ch-ua-mobile", "?0");
        httpHeaders.set("sec-ch-ua-platform", "\"Windows\"");
        httpHeaders.set("sec-fetch-dest", "empty");
        httpHeaders.set("sec-fetch-mode", "cors");
        httpHeaders.set("sec-fetch-site", "same-origin");
        httpHeaders.set("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        httpHeaders.set("x-requested-with", "XMLHttpRequest");

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        RecruitParam recruitParam = new RecruitParam();
        recruitParam.setRequestForm(1);
        recruitParam.setPage(10);
        recruitParam.setPageSize(20);
        recruitParam.setRecruitType(2);
        recruitParam.setPageSource(5001);
        recruitParam.setVisitorId("ebb5a2ba-7dcb-4e8d-8ae1-6f33ed7dcf95");

        params.add("requestFrom", recruitParam.getRequestForm());
        params.add("page", recruitParam.getPage());
        params.add("pageSize", recruitParam.getPageSize());
        params.add("recruitType", recruitParam.getRecruitType());
        params.add("pageSize", recruitParam.getPageSource());
        params.add("visitorId", recruitParam.getVisitorId());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<ResultBody> response = restTemplate.postForEntity(url, httpEntity, ResultBody.class);
        ResultBody body = response.getBody();

        NowCoderPage<RecruitData> data = JSON.parseObject(JSON.toJSONString(body.getData()), NowCoderPage.class);

        List<RecruitData> list = JSON.parseArray(JSON.toJSONString(data.getDatas()), RecruitData.class);
        return list;
    }


    private List<Object> handleResult(List<RecruitData> list){

        List<RecruitEntity> recruitEntities = new ArrayList<>();
        List<CompanyEntity> companyEntities  = new ArrayList<>();
        for (RecruitData recruitData : list) {
            RecruitEntity recruitEntity = new RecruitEntity();
            BeanUtils.copyProperties(recruitData,recruitEntity);
            recruitEntity.setTitle(recruitData.getJobName());
            recruitEntity.setContent(recruitData.getExt());
            recruitEntities.add(recruitEntity);

            RecommendInternCompany recommendInternCompany = recruitData.getRecommendInternCompany();
            CompanyEntity companyEntity = new CompanyEntity();
            BeanUtils.copyProperties(recommendInternCompany,companyEntity);
            companyEntity.setId(recommendInternCompany.getCompanyId());
            companyEntities.add(companyEntity);
            log.debug(recruitData.toString());
        }

        recruitService.saveOrUpdateBatch(recruitEntities);
        companyService.saveOrUpdateBatch(companyEntities);

        return null;
    }

}
