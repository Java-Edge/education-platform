package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.InterviewArticleDto;
import com.javagpt.back.entity.InterviewExperienceArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.jws.soap.InitParam;
import java.util.List;

/**
* @author MSIK
* @description 针对表【interview_experience_article】的数据库操作Mapper
* @createDate 2023-07-24 23:36:50
* @Entity com.javagpt.back.entity.InterviewExperienceArticleEntity
*/
@Mapper
public interface InterviewExperienceArticleMapper extends BaseMapper<InterviewExperienceArticleEntity> {

    /**
     * 分页查询
     *
     * @param dto
     * @param page
     */
    IPage<ArticleVO> selectByCondition(@Param("dto") InterviewArticleDto dto, @Param("page") Page<ArticleVO> page);
}




