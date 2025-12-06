package com.javaedge.infra.mysql.repository.impl;

import com.javaedge.dict.entity.DictionaryTypeEntity;
import com.javaedge.dict.repository.DictTypeRepository;
import com.javaedge.infra.mysql.mapper.TDictionaryTypeMapper;
import com.javaedge.infra.mysql.po.TDictionaryType;
import com.javaedge.infra.mysql.repository.common.BaseCrudRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author JavaEdge
 * @date 2024/3/5
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class DictTypeRepositoryImpl extends BaseCrudRepositoryImpl<TDictionaryTypeMapper, TDictionaryType, DictionaryTypeEntity> implements DictTypeRepository {
}
