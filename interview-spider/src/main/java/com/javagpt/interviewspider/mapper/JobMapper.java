package com.javagpt.interviewspider.mapper;

import com.javagpt.interviewspider.entity.JobEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author JavaGPT
* @description 针对表【job(职位分类表)】的数据库操作Mapper
* @createDate 2023-08-01 13:52:46
* @Entity com.javagpt.interviewspider.entity.JobEntity
*/
@Mapper
public interface JobMapper extends BaseMapper<JobEntity> {

}




