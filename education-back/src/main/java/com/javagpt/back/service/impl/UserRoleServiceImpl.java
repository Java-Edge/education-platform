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
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        // 提前设置大小，避免扩容
        List<Integer> roleIds = new ArrayList<>(userRoles.size());
        for (UserRole ur : userRoles) {
            roleIds.add(ur.getRoleId());
        }
        /**
         * 用户可能没有对应角色，如果为空进行批量查询会报错
         */
        if (roleIds.size() <= 0) {
            return new ArrayList<>();
        }
        return roleMapper.selectBatchIds(roleIds).stream().map(Role::getPermission).collect(Collectors.toList());
    }
}
