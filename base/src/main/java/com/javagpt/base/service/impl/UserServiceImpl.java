package com.javagpt.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.base.dto.UserDTO;
import com.javagpt.base.entity.UserEntity;
import com.javagpt.base.service.UserService;
import com.javagpt.base.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author MSIK
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-08-05 18:49:02
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity>
    implements UserService{

    @Override
    public boolean chkUsername(String username) {

        return true;
    }

    @Override
    public int insertUser(UserDTO user) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        userEntity.setCreatTime(new Date());
        userEntity.setUpdateTime(new Date());

        save(userEntity);
        return 1;
    }

    @Override
    public UserEntity selectUser(String username, String password) {

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUsername,username)
                .eq(UserEntity::getPassword,password);

        UserEntity userEntity = this.getBaseMapper().selectOne(wrapper);
        return userEntity;
    }
}




