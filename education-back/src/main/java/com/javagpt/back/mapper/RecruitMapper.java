package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.RecruitQO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.entity.RecruitPO;
import com.javagpt.back.vo.RecruitEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RecruitMapper extends BaseMapper<RecruitPO> {

    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    Page<RecruitEntity> queryPage(@Param("page") Page<RecruitPO> page, @Param("dto") RecruitQO dto);


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RecruitEntity queryById(String id);

}
