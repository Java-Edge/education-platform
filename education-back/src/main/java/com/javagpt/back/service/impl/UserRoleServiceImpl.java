package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.Role;
import com.javagpt.back.entity.UserRole;
import com.javagpt.back.mapper.RoleMapper;
import com.javagpt.back.mapper.UserRoleMapper;
import com.javagpt.back.service.UserRoleService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    UserRoleMapper userRoleMapper;

    @Resource
    RoleMapper roleMapper;

    @Override
    public List<String> getByUserId(long userId) {
        QueryWrapper<UserRole> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(qw);
        // 提前设置大小，避免扩容
        List<Integer> roleIds = new ArrayList<>(userRoles.size());
        for (UserRole userRole : userRoles) {
            roleIds.add(userRole.getRoleId());
        }
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        return roleMapper.selectBatchIds(roleIds).stream().map(Role::getPermission).collect(Collectors.toList());
    }
}