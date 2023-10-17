package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-10-17
 */
public interface CommentService extends IService<Comment> {

    Page<Comment> getComponentByFid(Integer fId, Integer current, Integer size);
}
