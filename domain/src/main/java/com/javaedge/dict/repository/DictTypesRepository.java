package com.javaedge.dict.repository;

import com.javaedge.dict.entity.DictionaryTypeEntity;
import org.springframework.data.repository.ListCrudRepository;

/**
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
