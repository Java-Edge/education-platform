package com.javagpt.back.controller;

import com.javagpt.application.file.FileApplicationService;
import com.javagpt.application.file.FileDTO;
import com.javagpt.common.annotation.RespSuccess;
import com.javagpt.common.constant.EPConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(EPConstant.API_V1 + "/file")
@RespSuccess
@RequiredArgsConstructor
public class FileController {

    private final FileApplicationService fileApplicationService;

    @PostMapping(value = "/uploadFile")
    public FileDTO uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        FileDTO fileDTO = fileApplicationService.saveFile(multipartFile);
        return fileDTO;
    }

    //@PreAuthorize("hasAuthority('mp:file:download')")
    @GetMapping(value = "/downloadFile")
    public void download(@RequestParam(value = "id") Long id, HttpServletResponse response) throws IOException {
//        fileApplicationService.downloadFileStream(response, id);
    }

    @GetMapping(value = "/info")
    public FileDTO fileInfo(@RequestParam(value = "id") Long id) throws IOException {
        return fileApplicationService.findById(id);
    }

    @GetMapping(value = "/downloadFile2")
    public void download2(@RequestParam(value = "id") Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        fileApplicationService.downloadVideo(request, response, id);
    }
}
