package com.javaedge.common.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.javaedge.common.req.BaseBean;
import com.javaedge.common.req.BasePageBean;

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
     * @param basePageReq 包含分页请求信息的对象
     * @return 包含分页后的Bean列表
     */
    IPage<Bean> page(BasePageBean basePageReq);

    /**
     * 分页
     *
     * @param basePageReq
     * @param dtoClass 还接受一个DTO类
     * @param <DTO>
     * @return 返回DTO类型的分页结果
     */
    <DTO> IPage<DTO> pageWithDTO(BasePageBean basePageReq, Class<DTO> dtoClass);

    /**
     * 分页查询
     *
     * @param basePageReq
     * @param consumer 多了一个Consumer参数 用于对查询结果进行消费或处理。
     * @return
     */
    IPage<Bean> page(BasePageBean basePageReq, Consumer<List<Bean>> consumer);

    /**
     * 综合了page2和page的功能，既支持DTO类型转换，也支持在分页查询后进行额外处理
     *
     * @param basePageBean
     * @param consumer
     * @param dtoClass
     * @param <DTO>
     * @return
     */
    <DTO> IPage<DTO> pageWithDTO(BasePageBean basePageBean, Class<DTO> dtoClass, Consumer<List<DTO>> consumer);


    default <T> IPage<T> emptyPage(BasePageBean basePageBean) {
        return new Page<>(basePageBean.getPage(), basePageBean.getSize());
    }
}
