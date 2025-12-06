package com.javaedge.back.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.back.dto.InnerRecommendQueryDTO;
import com.javaedge.back.entity.CompanyDiscussion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface CompanyDiscussionMapper extends BaseMapper<CompanyDiscussion> {

    IPage<CompanyDiscussion> listByCondition(@Param("page") Page<CompanyDiscussion> page,
                                             @Param("dto") InnerRecommendQueryDTO param);
}
