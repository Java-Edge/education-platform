package com.javaedge.common.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javagpt.common.req.BaseBean;
import com.javagpt.common.req.BasePageBean;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface IBaseRepository<Bean extends BaseBean> {
    /**
     * 新增
     *
     * @param bean
     */
    Bean saveBean(Bean bean);

    /**
     * 更新
     *
     * @param bean
     */
    void updateBean(Bean bean);


    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    Bean findById(Long id);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Long id);

    /**
     * 批量删除
     *
     * @param ids
     */
    void deleteByIds(Collection<Long> ids);

    /**
     * 根据id列表查询
     *
     * @param ids
     * @return
     */
    List<Bean> findByIds(Collection<Long> ids);

    /**
     * 获取所有
     *
     * @return
     */
    List<Bean> findAll();

    /**
     * 分页
     *
     * @param basePageReq
     * @return
     */
    IPage<Bean> page(BasePageBean basePageReq);

    /**
     * 分页
     *
     * @param basePageReq
     * @param dtoClass
     * @param <DTO>
     * @return
     */
    <DTO> IPage<DTO> page2(BasePageBean basePageReq, Class<DTO> dtoClass);

    /**
     * 分页查询
     *
     * @param basePageReq
     * @param consumer
     * @return
     */
    IPage<Bean> page(BasePageBean basePageReq, Consumer<List<Bean>> consumer);

    /**
     * 分页查询
     *
     * @param basePageBean
     * @param consumer
     * @param dtoClass
     * @param <DTO>
     * @return
     */
    <DTO> IPage<DTO> page2(BasePageBean basePageBean, Class<DTO> dtoClass, Consumer<List<DTO>> consumer);


    default <T> IPage<T> emptyPage(BasePageBean basePageBean) {
        return new Page<>(basePageBean.getPage(), basePageBean.getSize());
    }
}
