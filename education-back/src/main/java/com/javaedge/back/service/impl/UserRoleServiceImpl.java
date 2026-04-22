package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.entity.Role;
import com.javaedge.back.entity.UserRole;
import com.javaedge.back.mapper.RoleMapper;
import com.javaedge.back.mapper.UserRoleMapper;
import com.javaedge.back.service.UserRoleService;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            // JDK 9+ List.of: 返回不可变空列表，表达“无结果”的语义更明确。
            return List.of();
        }
        // JDK 16+ Stream#toList: 直接返回不可变结果列表。
        return roleMapper.selectBatchIds(roleIds).stream().map(Role::getPermission).toList();
    }
}
