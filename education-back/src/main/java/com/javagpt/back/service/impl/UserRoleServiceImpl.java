package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.Role;
import com.javagpt.back.entity.UserRole;
import com.javagpt.back.mapper.RoleMapper;
import com.javagpt.back.mapper.UserRoleMapper;
import com.javagpt.back.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-09-25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    RoleMapper roleMapper;

    @Override
    public List<String> getByUserId(Integer userId) {
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(qw);
        List<String> roleNames = new ArrayList<>();
        for (UserRole ur : userRoles) {
            Role role = roleMapper.selectById(ur.getRoleId());
            roleNames.add(role.getName());
        }
        return roleNames;
    }
}
