package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ArticleDTO;
import com.javagpt.back.entity.InterviewEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InterviewMapper extends BaseMapper<InterviewEntity> {

    /**
     * 分页查询
     *
     * @param dto
     * @param page
     */
    IPage<ArticleVO> selectByCondition(@Param("dto") ArticleDTO dto, @Param("page") Page<ArticleVO> page);
}




