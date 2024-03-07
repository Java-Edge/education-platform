package com.javagpt.dict.repository;

import com.javagpt.dict.entity.DictionaryTypeEntity;
import org.springframework.data.repository.ListCrudRepository;

/**
 * JPA实现
 *
 * @author JavaEdge
 * @date 2024/3/5
 */
public interface DictTypesRepository extends ListCrudRepository<DictionaryTypeEntity, Long> {

    /**
     * 如果经常在服务层repository.findById(..).orElseThrow(..)中重复
     * 请在存储库接口中添加默认方法“findByIdOrThrow”
     *
     * @param id 主键
     * @return
     */
    default DictionaryTypeEntity findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("Dictionary type not found"));
    }
}
