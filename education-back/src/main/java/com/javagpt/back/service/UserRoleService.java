package com.javagpt.back.service;

import com.javagpt.back.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-09-25
 */
public interface UserRoleService extends IService<UserRole> {

    List<String> getByUserId(Integer id);
}
