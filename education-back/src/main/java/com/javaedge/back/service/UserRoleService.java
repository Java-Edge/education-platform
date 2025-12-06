package com.javaedge.back.service;

import com.javaedge.back.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {

    List<String> getByUserId(long id);
}
