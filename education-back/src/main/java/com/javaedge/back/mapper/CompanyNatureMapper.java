package com.javaedge.back.mapper;

import com.javaedge.back.entity.CompanyNature;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaedge.back.vo.CompanyNatureVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyNatureMapper extends BaseMapper<CompanyNature> {

    List<CompanyNatureVO> getList();

}
