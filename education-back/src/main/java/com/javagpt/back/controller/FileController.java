package com.javagpt.back.controller;

import com.javagpt.application.context.UserAppContextHolder;
import com.javagpt.application.file.FileApplicationService;
import com.javagpt.application.file.FileDTO;
import com.javagpt.common.annotation.RespSuccess;
import com.javagpt.common.constant.EPConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "文件接口")
@Slf4j
@RestController
@RequestMapping(EPConstant.API_V1 + "/file")
@RespSuccess
@RequiredArgsConstructor
public class FileController {

    private final FileApplicationService fileApplicationService;

    @ApiOperation("通用文件上传")
    @PostMapping(value = "/uploadFile")
    public FileDTO uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        Long enterpriseId = UserAppContextHolder.getCurrentUser().getEnterpriseId();
        FileDTO fileDTO = fileApplicationService.saveFile(enterpriseId, multipartFile);
        return fileDTO;
    }

    //@PreAuthorize("hasAuthority('mp:file:download')")
    @ApiOperation("下载文件")
    @GetMapping(value = "/downloadFile")
    public void download(@RequestParam(value = "id") Long id, HttpServletResponse response) throws IOException {
        fileApplicationService.downloadFile(response, id, UserAppContextHolder.getCurrentUser().getEnterpriseId());
    }

    @ApiOperation("查看文件信息")
    @GetMapping(value = "/info")
    public FileDTO fileInfo(@RequestParam(value = "id") Long id) throws IOException {
        return fileApplicationService.findById(id);
    }

    @ApiOperation("下载视频")
    @GetMapping(value = "/downloadFile2")
    public void download2(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileApplicationService.downloadVideo(request, response, id, UserAppContextHolder.getCurrentUser().getEnterpriseId());
    }
}
