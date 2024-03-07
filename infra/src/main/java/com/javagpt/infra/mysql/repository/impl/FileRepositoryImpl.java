package com.javagpt.infra.mysql.repository.impl;

import com.javagpt.file.entity.FileEntity;
import com.javagpt.file.repository.FileRepository;
import com.javagpt.infra.mysql.mapper.TFileMapper;
import com.javagpt.infra.mysql.po.TFile;
import com.javagpt.infra.mysql.repository.common.BaseCrudRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl extends BaseCrudRepositoryImpl<TFileMapper, TFile, FileEntity> implements FileRepository {
}