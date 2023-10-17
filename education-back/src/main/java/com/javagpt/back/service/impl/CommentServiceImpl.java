package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.entity.Comment;
import com.javagpt.back.mapper.CommentMapper;
import com.javagpt.back.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    @Override
    public Page<Comment> getComponentByFid(Integer fId, Integer current, Integer size) {
        Page<Comment> page = new Page<>(current, size);
        QueryWrapper<Comment> qw = new QueryWrapper<>();
        qw.eq("fe_id", fId);
        qw.eq("status", 1);
        commentMapper.selectPage(page, qw);
        return page;
    }
}
