package com.javagpt.back.config.springsecurity;

import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.dto.ResultStatus;
import com.javagpt.back.entity.UserEntity;
import com.javagpt.back.mapper.UserMapper;
import com.javagpt.back.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author 千祎来了
 * @date 2023/9/24 19:26
 */
@Component("ss") // SpringSecurity 缩写
public class MyExpressionRoot {

    @Autowired
    UserMapper userMapper;

    public boolean hasAuthority(String authority){
        System.out.println("abc");
        //获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("112312312");
        String userId = (String) authentication.getPrincipal();
        UserEntity userEntity = userMapper.selectById(userId);
        if (userEntity == null) {
            return false;
        }

        //判断用户权限集合中是否存在authority
//        return permissions.contains(authority);
        return userEntity.getUsername().equals(authority);
    }
}
