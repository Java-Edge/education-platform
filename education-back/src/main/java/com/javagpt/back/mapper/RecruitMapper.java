package com.javagpt.back.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.RecruitDTO;
import com.javagpt.back.entity.Recruit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.RecruitVO;
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
public interface RecruitMapper extends BaseMapper<Recruit> {

    /**
     * 分页查询
     *
     * @param page
     * @param dto
     * @return
     */
    Page<RecruitVO> queryPage(@Param("page") Page<Recruit> page, @Param("dto") RecruitDTO dto);


    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    RecruitVO queryById(Integer id);

}
