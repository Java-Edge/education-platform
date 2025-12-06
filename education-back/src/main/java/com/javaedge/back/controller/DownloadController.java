package com.javaedge.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.javaedge.application.file.FileApplicationService;
import com.javaedge.application.file.FileDTO;
import com.javaedge.common.resp.ResultBody;
import com.javaedge.file.entity.FileEntity;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

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

    @PostMapping("upload")
    public ResultBody uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        FileDTO fileDTO = fileApplicationService.saveFile(multipartFile);
        return ResultBody.success(fileDTO);
    }

    @PostMapping(value = "/downloadFile/{id}")
    public ResultBody download(@PathVariable Long id, HttpServletResponse response) throws IOException {
        URL url = fileApplicationService.downloadFileUrl(response, id);
        return ResultBody.success(url);
    }
}