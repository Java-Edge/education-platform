package com.javagpt.infra.mysql.repository.impl;

import com.javagpt.dict.entity.DictionaryTypeEntity;
import com.javagpt.dict.repository.DictTypeRepository;
import com.javagpt.infra.mysql.mapper.TDictionaryTypeMapper;
import com.javagpt.infra.mysql.po.TDictionaryType;
import com.javagpt.infra.mysql.repository.common.BaseCrudRepositoryImpl;
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
