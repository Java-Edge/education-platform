package com.javagpt.back.mapper;

import com.javagpt.back.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 公司主体 Mapper 接口
 * </p>
 *
 * @author zqy
 * @since 2023-08-10
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

}
