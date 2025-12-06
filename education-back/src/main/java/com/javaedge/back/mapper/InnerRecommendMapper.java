package com.javaedge.back.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.back.dto.InnerRecommendQueryDTO;
import com.javaedge.back.entity.InnerRecommend;
import org.apache.ibatis.annotations.Param;

public interface InnerRecommendMapper extends BaseMapper<InnerRecommend> {

    IPage<InnerRecommend> selectByCondition(@Param("page") Page<InnerRecommend> page,
                                            @Param("dto") InnerRecommendQueryDTO dto);
}




