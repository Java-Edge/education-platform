package com.javagpt.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.user.dto.UserDTO;
import com.javagpt.user.entity.UserEntity;
import com.javagpt.user.mapper.UserMapper;
import com.javagpt.user.service.UserService;
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
        implements UserService {

    @Override
    public boolean chkUsername(String username) {
        UserEntity userEntity = this.getBaseMapper()
                .selectOne(new LambdaQueryWrapper<UserEntity>()
                        .eq(UserEntity::getUsername, username));
        return userEntity != null;
    }

    @Override
    public int insertUser(UserDTO user) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setCreatTime(new Date());
        userEntity.setUpdateTime(new Date());

        save(userEntity);
        return 1;
    }

    @Override
    public UserEntity selectUser(String username, String password) {

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUsername, username)
                .eq(UserEntity::getPassword, password);

        UserEntity userEntity = this.getBaseMapper().selectOne(wrapper);
        return userEntity;
    }
}




