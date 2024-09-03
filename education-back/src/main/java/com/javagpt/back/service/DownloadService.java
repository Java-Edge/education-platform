package com.javagpt.back.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.javagpt.application.file.FileDTO;
import com.javagpt.back.entity.ArticleEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DownloadService extends IService<ArticleEntity> {

    Page<ArticleEntity> listByPage(Integer current, Integer size);

    ArticleEntity selectById(Integer id);
}
