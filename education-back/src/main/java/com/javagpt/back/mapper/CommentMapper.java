package com.javagpt.back.mapper;

import com.javagpt.back.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zqy
 * @since 2023-10-17
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
