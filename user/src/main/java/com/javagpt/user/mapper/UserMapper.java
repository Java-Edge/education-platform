package com.javagpt.user.mapper;

import com.javagpt.user.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author MSIK
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-08-05 18:49:02
* @Entity com.javagpt.user.entity.UserEntity
*/
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}




