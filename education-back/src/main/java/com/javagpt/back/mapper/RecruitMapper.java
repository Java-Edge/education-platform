package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.Recruit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.RecruitEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqy
 * @since 2023-07-23
 */
@Mapper
public interface RecruitMapper extends BaseMapper<com.javagpt.back.entity.Recruit> {

    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    Page<RecruitEntity> queryPage(@Param("page") Page<com.javagpt.back.entity.Recruit> page, @Param("dto") Recruit dto);


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RecruitEntity queryById(String id);

}
