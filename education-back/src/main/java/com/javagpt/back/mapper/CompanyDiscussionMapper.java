package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.CompanyDiscussion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface CompanyDiscussionMapper extends BaseMapper<CompanyDiscussion> {

    IPage<CompanyDiscussion> listByCondition(@Param("page") Page<CompanyDiscussion> page,
                                             @Param("dto") InnerRecommendQueryDTO param);
}
