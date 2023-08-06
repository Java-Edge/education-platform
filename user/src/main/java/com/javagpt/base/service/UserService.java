package com.javagpt.base.service;

import com.javagpt.base.dto.UserDTO;
import com.javagpt.base.entity.UserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author MSIK
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-08-05 18:49:02
*/
public interface UserService extends IService<UserEntity> {

    /**
     * 检查用户名
     *
     * @param username
     * @return
     */
    boolean chkUsername(String username);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    int insertUser(UserDTO user);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param md5
     * @return
     */
    UserEntity selectUser(String username, String md5);
}
