package com.javaedge.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.back.entity.Comment;
import com.javaedge.back.entity.UserPO;
import com.javaedge.back.mapper.CommentMapper;
import com.javaedge.back.mapper.UserMapper;
import com.javaedge.back.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedge.back.util.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqy
 * @since 2023-10-17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page<Comment> getComponentByFid(Long fId, Integer current, Integer size) {
        Page<Comment> page = new Page<>(current, size);
        QueryWrapper<Comment> qw = new QueryWrapper<>();
        qw.eq("fe_id", fId);
        qw.eq("status", 1);
        commentMapper.selectPage(page, qw);
        /**
         * 评论分页，只有5条数据，这里遍历用户开销不大
         */
        for (Comment comment : page.getRecords()) {
            Integer userId = comment.getUserId();
            UserPO userPO = userMapper.selectById(userId);
            comment.setUser(userPO);
        }
        return page;
    }

    @Override
    public void saveComment(Comment comment, HttpServletRequest request) {
        Integer userId = UserContextHolder.getCurrentUserId(request);
        if (userId == null) {
            throw new RuntimeException("用户未登录，无法评论！");
        }
        comment.setUserId(userId);
        this.getBaseMapper().insert(comment);
    }
}
