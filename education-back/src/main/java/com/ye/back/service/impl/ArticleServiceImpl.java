package com.ye.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ye.back.entity.ArticleEntity;
import com.ye.back.entity.SourceCourseEntity;
import com.ye.back.mapper.ArticleMapper;
import com.ye.back.mapper.SourceCourseMapper;
import com.ye.back.service.ArticleService;
import com.ye.back.service.SourceCourseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MSIK
 * @description 针对表【source_course】的数据库操作Service实现
 * @createDate 2023-07-09 13:40:08
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity>
        implements ArticleService {

}




