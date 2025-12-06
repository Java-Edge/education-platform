package com.javaedge.file.entity;

import com.javaedge.common.entity.BaseAuditEntity;
import com.javaedge.common.util.IdUnsafeGenerator;
import com.javaedge.file.constant.FileStorageTypeEnum;
import com.javaedge.file.repository.FileRepository;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class FileEntity extends BaseAuditEntity<FileEntity, FileRepository> {

    /**
     * 存储类型 1、百度云
     */
    private Integer storageType;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件后缀,如pdf，word
     */
    private String suffix;

    /**
     * 文件存储路径
     */
    private String path;

    @Override
    public FileEntity save() {
        setId(IdUnsafeGenerator.nextId());
        if (Objects.isNull(storageType)) {
            setStorageType(FileStorageTypeEnum.ALIYUN.getType());
        }
        if (Objects.isNull(suffix)) {
            setSuffix("");
        }
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder
                .append("img/")
                .append(UUID.randomUUID().toString().replace("-", ""))
                .append(".")
                .append(suffix);
        setPath(pathBuilder.toString());
        return repository().saveBean(this);
    }

    public String fileFullName() {
        return name + "." + suffix;
    }
}