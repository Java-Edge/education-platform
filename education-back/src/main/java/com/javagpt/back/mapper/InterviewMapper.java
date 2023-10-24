package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.InterviewDTO;
import com.javagpt.back.entity.InterviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InterviewMapper extends BaseMapper<InterviewEntity> {

    /**
     * 分页查询
     */
    IPage<InterviewEntity> selectByCondition(@Param("dto") InterviewDTO dto, @Param("page") Page<InterviewEntity> page);
}




