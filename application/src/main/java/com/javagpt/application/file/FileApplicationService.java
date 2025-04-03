package com.javagpt.application.file;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.common.constant.CommonConstants;
import com.javagpt.common.exception.BusinessRuntimeException;
import com.javagpt.common.oos.OssService;
import com.javagpt.common.req.BasePageBean;
import com.javagpt.common.util.ModelUtils;
import com.javagpt.common.util.SpringResponseUtils;
import com.javagpt.file.entity.FileEntity;
import com.javagpt.file.repository.FileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileApplicationService {

    private final OssService ossService;

    private final FileRepository fileRepository;

    public FileDTO findById(Long id) {
        FileEntity fileEntity = fileRepository.findById(id);
        return ModelUtils.convert(fileEntity, FileDTO.class);
    }

    @Transactional
    public FileDTO saveFile(MultipartFile file) {
        FileEntity fileEntity = saveFile(file, null);
        return ModelUtils.convert(fileEntity, FileDTO.class);
    }

    public FileEntity saveFile(MultipartFile file, String fileName) {
        String originalFilename = file.getOriginalFilename();
        String name = StringUtils.isBlank(fileName) ? FilenameUtils.getBaseName(originalFilename) : fileName;
        String suffix = FilenameUtils.getExtension(originalFilename);
        long size = file.getSize();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(name).setSuffix(suffix).setSize(size);
        fileEntity = fileEntity.save();
        String objectName = fileEntity.getPath();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("saveFile error:", e);
            throw BusinessRuntimeException.error("上传文件失败");
        }
        ossService.uploadFile(inputStream, objectName);
//        IOUtils.closeQuietly(inputStream);
        return fileEntity;
    }

    public URL downloadFileUrl(HttpServletResponse response, Long fileId) throws IOException {
        FileEntity fileEntity = fileRepository.findById(fileId);
        if (fileEntity == null) {
            throw BusinessRuntimeException.error("无效的文件Id");
        }
        String key = fileEntity.getPath();

        return ossService.downloadFile(key);
    }

    public void downloadFileStream(HttpServletResponse response, Long fileId) throws IOException {
        FileEntity fileEntity = fileRepository.findById(fileId);
        if (fileEntity == null) {
            throw BusinessRuntimeException.error("无效的文件Id");
        }
        String key = fileEntity.getPath();

        InputStream inputStream = ossService.downloadFileStream(key);
        // 由于inputStream得到 File
        SpringResponseUtils.writeAndFlushResponse(inputStream, response, fileEntity.fileFullName());
    }


    public void downloadVideo(HttpServletRequest request, HttpServletResponse response, Long fileId) throws IOException {

        FileEntity fileEntity = fileRepository.findById(fileId);
        if (fileEntity == null) {
            throw BusinessRuntimeException.error("无效的文件Id");
        }
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
        Long fileSize = fileEntity.getSize();
        long start = 0, end = fileSize - 1;
        //判断前端需不需要分片下载
        if (StringUtils.isNotBlank(request.getHeader("Range"))) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            String numRange = request.getHeader("Range").replaceAll("bytes=", "");
            String[] strRange = numRange.split("-");
            if (strRange.length == 2) {
                start = Long.parseLong(strRange[0].trim());
                end = Long.parseLong(strRange[1].trim());
                //若结束字节超出文件大小 取文件大小
                if (end > fileSize - 1) {
                    end = fileSize - 1;
                }
            } else {
                //若只给一个长度  开始位置一直到结束
                start = Long.parseLong(numRange.replaceAll("-", "").trim());
            }
        }
        long rangeLength = end - start + 1;
        String contentRange = new StringBuffer("bytes ").append(start).append("-").append(end).append("/").append(fileSize).toString();
        response.setHeader(HttpHeaders.CONTENT_RANGE, contentRange);
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(rangeLength));
        String key = fileEntity.getPath();
        InputStream inputStream = ossService.downloadVideo(key, start, end);
        SpringResponseUtils.writeAndFlushResponse(inputStream, response, fileEntity.fileFullName());
    }

    public IPage<FileEntity> listByPage(Integer current, Integer size) {
        Page<FileEntity> page = new Page<>(current, size);
        QueryWrapper<FileEntity> qw = new QueryWrapper<>();
        qw.eq(CommonConstants.DELETE_FLAG, 0);
        qw.orderByDesc("create_time");
        BasePageBean  pageBean = new BasePageBean();
        pageBean.setPage(current);
        pageBean.setSize(size);
        page.setCurrent(pageBean.getPage());
        page.setSize(pageBean.getSize());
        IPage<FileEntity> fileEntityIPage = fileRepository.page(pageBean);
        return fileEntityIPage;
    }
}