package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ArticleDTO;
import com.javagpt.back.entity.CourseEntity;
import com.javagpt.back.vo.ArticleVO;
import com.javagpt.back.vo.course.CourseRoadmapVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoadmapMapper extends BaseMapper<CourseEntity> {

}
