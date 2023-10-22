package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.InnerRecommendQueryDTO;
import com.javagpt.back.entity.InnerRecommend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.InnerRecommendVO;
import org.apache.ibatis.annotations.Param;

/**
* @author 26314
* @description 针对表【inner_recommend】的数据库操作Mapper
* @createDate 2023-10-22 23:10:25
* @Entity com.javagpt.back.entity.InnerRecommend
*/
public interface InnerRecommendMapper extends BaseMapper<InnerRecommend> {

    /**
     * 分页查询
     *
     * @param dto
     * @param page
     */
    IPage<InnerRecommendVO> selectByCondition(@Param("page") Page<InnerRecommendVO> page, @Param("dto") InnerRecommendQueryDTO dto);
}




