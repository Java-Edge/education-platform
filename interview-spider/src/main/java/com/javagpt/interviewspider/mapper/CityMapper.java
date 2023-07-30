package com.javagpt.interviewspider.mapper;

import com.javagpt.interviewspider.entity.CityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author MSIK
* @description 针对表【city(城市)】的数据库操作Mapper
* @createDate 2023-07-30 20:35:56
* @Entity com.javagpt.interviewspider.entity.CityEntity
*/
@Mapper
public interface CityMapper extends BaseMapper<CityEntity> {

}




