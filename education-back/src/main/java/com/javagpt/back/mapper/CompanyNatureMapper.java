package com.javagpt.back.mapper;

import com.javagpt.back.entity.CompanyNature;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javagpt.back.vo.CompanyNatureVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyNatureMapper extends BaseMapper<CompanyNature> {

    List<CompanyNatureVO> getList();

}
