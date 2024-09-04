package com.javagpt.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javagpt.application.file.FileApplicationService;
import com.javagpt.application.file.FileDTO;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.file.entity.FileEntity;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传下载
 */
@RestController
@RequestMapping("/download")
public class DownloadController {

    @Resource
    private FileApplicationService fileApplicationService;

    @GetMapping("/listByPage")
    public ResultBody listByPage(Integer current, Integer size) {
        IPage<FileEntity> fileEntityIPage = fileApplicationService.listByPage(current, size);
        return ResultBody.success(fileEntityIPage);
    }

    @GetMapping("/getById/{id}")
    public ResultBody getById(@PathVariable Long id) {
        FileDTO fileDTO = fileApplicationService.findById(id);
        return ResultBody.success(fileDTO);
    }

    // 上传接口 upload
    @PostMapping("upload")
    public ResultBody uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        FileDTO fileDTO = fileApplicationService.saveFile(multipartFile);
        return ResultBody.success(fileDTO);
    }
}