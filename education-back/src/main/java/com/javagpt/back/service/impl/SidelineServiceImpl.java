package com.javagpt.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.entity.Sideline;
import com.javagpt.back.mapper.SidelineMapper;
import com.javagpt.back.service.SidelineService;
import com.javagpt.back.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SidelineServiceImpl extends ServiceImpl<SidelineMapper, Sideline> implements SidelineService {

    @Autowired
    private UserRoleService userRoleService;


    @Override
    public Page<Sideline> selectPage(Integer current, Integer size) {
        Page<Sideline> page = new Page<>(current, size);
        QueryWrapper<Sideline> qw = new QueryWrapper<>();
        qw.eq("delete_flag", 0);
        qw.orderByDesc("create_time");
        qw.select("id", "title", "left(des, 50) des", "href", "img", "create_time", "permission");
        Page<Sideline> sidelinePage = this.getBaseMapper().selectPage(page, qw);
        /**
         * 访问副业时，需要登陆，登陆时会向 SecurityContext 中存储用户 id，因此这里一定可以取到
         */
        Integer userId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> userPermission = userRoleService.getByUserId(userId);
        for (Sideline sl : sidelinePage.getRecords()) {

            String permission = sl.getPermission();

            /**
             * 如果所需权限为空，则不需要判断
             */
            if (StringUtils.isBlank(permission)) {
                continue;
            }
             JSONArray permissions = JSON.parseArray(permission);
            boolean permit = false;
            for (String up : userPermission) {
                if (permissions.contains(up)) {
                    permit = true;
                    break;
                }
            }
            if (!permit) {
                sl.setTitle("权限不足，无法查看");
                sl.setDes("权限不足，无法查看");
                // 这里不能设置 href 为空，否则前端判断会不展示
                sl.setHref("");
                sl.setImg("");
            }
        }
        return sidelinePage;
    }
}
