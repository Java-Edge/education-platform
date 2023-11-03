package com.javagpt.back.service;

import com.javagpt.back.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {

    List<String> getByUserId(Integer id);
}
