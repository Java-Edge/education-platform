package com.javagpt.interviewspider.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.interviewspider.data.boss.CityData;
import com.javagpt.interviewspider.data.boss.SiteGroupInfo;
import com.javagpt.interviewspider.dto.BossResult;
import com.javagpt.interviewspider.dto.BossResultBody;
import com.javagpt.interviewspider.dto.Result;
import com.javagpt.interviewspider.entity.CityEntity;
import com.javagpt.interviewspider.mapper.CityMapper;
import com.javagpt.interviewspider.service.BossCityService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MSIK
 * @description 针对表【city(城市)】的数据库操作Service实现
 * @createDate 2023-07-30 20:35:56
 */
@Service
public class BossCityServiceImpl extends ServiceImpl<CityMapper, CityEntity>
        implements BossCityService {

    @Resource
    private RestTemplate restTemplate;


    @Override
    public List<CityData> grabCities() {
        BossResult<CityData> result = getResponse();
        handleResponse(result);
        return null;
    }

    /**
     * 获取城市信息
     *
     * @return
     */
    public BossResult<CityData> getResponse() {
        try {
            String url = "https://www.zhipin.com/wapi/zpgeek/common/data/city/site.json";
            // 发送GET请求
            ResponseEntity<BossResultBody> response = restTemplate.getForEntity(url, BossResultBody.class);
            if (response.getStatusCodeValue() != 200) {
                log.error("response status code not 200");
            }
            BossResultBody<Result> body = response.getBody();
            BossResult<CityData> result = JSON.parseObject(JSON.toJSONString(body.getZpData()), BossResult.class);
            return result;
        } catch (Exception e) {
            log.error("can not grab boss data", e);
        }
        return null;
    }


    /**
     * 处理城市数据并且保存为实体类
     *
     * @param result
     * @return
     */
    private List<CityEntity> handleResponse(BossResult<CityData> result) {
        List<CityData> list = JSON.parseArray(JSON.toJSONString(result.getSiteList()), CityData.class);

        // 1. 将中国的城市分为省市存入集合中
        List<CityEntity> cityEntities = new ArrayList<>();
        for (CityData city : list) {
            CityEntity cityEntity = new CityEntity();
            BeanUtils.copyProperties(city, cityEntity);
            cityEntity.setId(city.getCode());
            cityEntity.setLevel(1);
            cityEntities.add(cityEntity);
            if (city.getSubLevelModelList() != null && city.getSubLevelModelList().size() > 0) {
                for (CityData cityData : city.getSubLevelModelList()) {
                    CityEntity entity = new CityEntity();
                    BeanUtils.copyProperties(cityData, entity);
                    entity.setId(cityData.getCode());
                    entity.setParentCode(cityEntity.getId());
                    entity.setLevel(2);
                    cityEntities.add(entity);
                }
            }
        }

        List<SiteGroupInfo> groupInfoList = result.getSiteGroup();

        // 2. 设置城市的首字母
        for (SiteGroupInfo siteGroupInfo : groupInfoList) {
            List<Long> ids = siteGroupInfo.getCityList().stream().map(item -> item.getCode()).collect(Collectors.toList());

            for (CityEntity cityEntity : cityEntities) {
                if (ids.contains(cityEntity.getId())) {
                    cityEntity.setFirstChar(siteGroupInfo.getFirstChar());
                }
            }
        }

        // 3. 设置热门城市
        List<CityData> hotCitySites = JSON.parseArray(JSON.toJSONString(result.getHotCitySites()), CityData.class);
        List<Long> hotCityIds = hotCitySites.stream().map(item -> item.getCode()).collect(Collectors.toList());
        for (CityEntity cityEntity : cityEntities) {
            if (hotCityIds.contains(cityEntity.getId())) {
                cityEntity.setIsHotCity(1);
            } else {
                cityEntity.setIsHotCity(0);
            }
        }

        saveOrUpdateBatch(cityEntities);

        return cityEntities;
    }


}




