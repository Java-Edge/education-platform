package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zqy
 * @since 2023-10-17
 */
public interface CommentService extends IService<Comment> {

    Page<Comment> getComponentByFid(Long fId, Integer current, Integer size);

    void saveComment(Comment comment, HttpServletRequest request);
}
