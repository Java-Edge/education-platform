package com.javagpt.back.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.dto.ResultStatus;
import com.javagpt.back.entity.Comment;
import com.javagpt.back.mapper.CommentMapper;
import com.javagpt.back.service.CommentService;
import com.javagpt.back.util.U;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zqy
 * @since 2023-10-17
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("getComponentByFid/{feId}")
    public ResultBody getComponentByFid(@PathVariable Integer feId, Integer current, Integer size) {
        Page<Comment> comments = commentService.getComponentByFid(feId, current, size);
        return ResultBody.success(comments);
    }

    @PostMapping("postComment")
    public ResultBody postComment(@RequestBody Comment comment) {
        try {
            Integer userId = U.currentUserId;
            if (userId == null) {
//                ResultBody result = new ResultBody(ResultStatus.LOGIN_FAIL_NOT, "请先登录！", null);
                return ResultBody.error("请登录之后再评论！");
            }
            comment.setUserId(userId);
            commentService.save(comment);
            return ResultBody.success();
        } catch (Exception e) {
//            ResultBody result = new ResultBody(ResultStatus.LOGIN_FAIL_NOT, "请先登录！", null);
            return ResultBody.error("请登录之后再评论！");
        }
    }
}

