package com.javagpt.interviewspider.service;

import com.javagpt.interviewspider.data.boss.CityData;
import com.javagpt.interviewspider.entity.CityEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author MSIK
* @description 针对表【city(城市)】的数据库操作Service
* @createDate 2023-07-30 20:35:56
*/
public interface BossCityService extends IService<CityEntity> {


    List<CityData> grabCities();

}
