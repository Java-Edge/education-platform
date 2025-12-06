package com.javaedge.infra.mysql.repository.impl;

import com.javaedge.file.entity.FileEntity;
import com.javaedge.file.repository.FileRepository;
import com.javaedge.infra.mysql.mapper.TFileMapper;
import com.javaedge.infra.mysql.po.TFile;
import com.javaedge.infra.mysql.repository.common.BaseCrudRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl extends BaseCrudRepositoryImpl<TFileMapper, TFile, FileEntity> implements FileRepository {
}