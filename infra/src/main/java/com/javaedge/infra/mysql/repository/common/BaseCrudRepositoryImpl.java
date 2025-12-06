package com.javaedge.infra.mysql.repository.common;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseMapper;
import com.github.yulichang.query.MPJQueryWrapper;
import com.javaedge.common.repository.IBaseRepository;
import com.javaedge.common.annotation.*;
import com.javaedge.common.constant.EPConstant;
import com.javaedge.common.constant.QueryEnum;
import com.javaedge.common.req.BaseBean;
import com.javaedge.common.req.BasePageBean;
import com.javaedge.common.util.ModelUtils;
import com.javaedge.common.util.MyStringUtils;
import com.javaedge.common.util.ReflectionKit;
import com.javaedge.common.util.ReflectionUtils;
import com.javaedge.infra.mysql.po.common.BasePO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public class BaseCrudRepositoryImpl<M extends BaseMapper<PO>, PO extends BasePO, BEAN extends BaseBean> extends ServiceImpl<M, PO> implements IBaseRepository<BEAN> {


    protected Class<PO> entityClass = this.currentModelClass();
    /**
     * 当前值对象的类
     */
    protected Class<BEAN> voClass = currentVoClass();


    Consumer<List<BEAN>> consumerNull = beans -> {
    };

    /**
     * 获取实体表名称
     *
     * @return
     */
    protected String currentTableName() {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(this.entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        return tableInfo.getTableName();
    }

    /**
     * 获取值对象的类
     *
     * @return
     */
    protected Class<BEAN> currentVoClass() {
        return (Class<BEAN>) ReflectionKit.getSuperClassGenericType(getClass(), 2);
    }

    protected Class<PO> currentModelClass() {
        return (Class<PO>) com.javaedge.common.util.ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    protected boolean isJoinMapper() {
        return this.baseMapper instanceof MPJBaseMapper;
    }

    @Override
    public BEAN saveBean(BEAN BEAN) {
        PO po = ModelUtils.convert(BEAN, entityClass);
        save(po);
        return ModelUtils.convert(po, voClass);
    }

    @Override
    public void updateBean(BEAN BEAN) {
        PO po = ModelUtils.convert(BEAN, entityClass);
        updateById(po);
    }


    @Override
    public BEAN findById(Long id) {
        PO po = getById(id);
        return ModelUtils.convert(po, voClass);
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id);
        removeById(id);
    }

    @Override
    public void deleteByIds(Collection<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            removeByIds(ids);
        }
    }

    @Override
    public List<BEAN> findByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<PO> poList = listByIds(ids);
        return ModelUtils.convertList(poList, voClass);
    }

    @Override
    public List<BEAN> findAll() {
        List<PO> poList = list();
        return ModelUtils.convertList(poList, voClass);
    }

    @Override
    public IPage<BEAN> page(BasePageBean basePageReq) {
        return page(basePageReq, null);
    }

    @Override
    public <DTO> IPage<DTO> pageWithDTO(BasePageBean basePageReq, Class<DTO> dtoClass) {
        return pageWithDTO(basePageReq, dtoClass, null);
    }

    @Override
    public IPage<BEAN> page(BasePageBean basePageBean, Consumer<List<BEAN>> consumer) {
        return pageWithDTO(basePageBean, voClass, consumer);
    }


    @Override
    public <DTO> IPage<DTO> pageWithDTO(BasePageBean basePageBean, Class<DTO> dtoClass, Consumer<List<DTO>> consumer) {
        IPage<DTO> newPage;
        IPage<PO> page = convertPageReq(basePageBean);
        if (isJoinMapper()) {
            MPJQueryWrapper<PO> mpjQueryWrapper = new MPJQueryWrapper<>();
            mpjQueryWrapper.selectAll(entityClass);
            handleQueryWrapper(basePageBean, mpjQueryWrapper, getEntityClass());
            String deleteFlagCol = getColumn("deleteFlag");
            mpjQueryWrapper.eq("t." + deleteFlagCol, EPConstant.NOT_DELETED);
            newPage = ((MPJBaseMapper<?>) this.baseMapper).selectJoinPage(page, dtoClass, mpjQueryWrapper);
        } else {
            QueryWrapper<PO> queryWrapper = new QueryWrapper<>();
            handleQueryWrapper(basePageBean, queryWrapper, getEntityClass());
            page = page(page, queryWrapper);
            newPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
            ((Page) newPage).setOrders(page.orders());
        }
        List<DTO> records = ModelUtils.convertList(page.getRecords(), dtoClass);
        if (records != null && !records.isEmpty()) {
            if (consumer != null) {
                consumer.accept(records);
            }
        }
        newPage.setRecords(records);
        return newPage;
    }

    protected IPage<PO> convertPageReq(BasePageBean basePageBean) {
        Page<PO> page = new Page<>(basePageBean.getPage(), basePageBean.getSize());
        List<OrderItem> orderItemList = getOrderItems(basePageBean.getOrder());
        if (!orderItemList.isEmpty()) {
            page.setOrders(orderItemList);
        }
        return page;
    }


    protected <DTO> IPage<DTO> convertPageReq(BasePageBean basePageBean, Class<DTO> dtoClass) {
        Page<DTO> page = new Page<>(basePageBean.getPage(), basePageBean.getSize());
        List<OrderItem> orderItemList = getOrderItems(basePageBean.getOrder());
        if (!orderItemList.isEmpty()) {
            page.setOrders(orderItemList);
        }
        return page;
    }

    private List<OrderItem> getOrderItems(String orderStr) {
        List<OrderItem> orderItemList = new ArrayList<>();
        Map<String, Field> modelFieldsMap = getFieldMap(getEntityClass());
        if (StringUtils.isNotBlank(orderStr)) {
            String[] items = orderStr.split(";");
            for (String item : items) {
                if (StringUtils.isBlank(item)) {
                    continue;
                }
                String[] fieldOrder = item.split(",");
                if (fieldOrder.length < 2) {
                    continue;
                }
                String fieldName = fieldOrder[0].trim();
                String order = fieldOrder[1].trim();
                if (modelFieldsMap.get(fieldName) == null) {
                    continue;
                }
                String column = getColumn(fieldName);
                if (StringUtils.equalsIgnoreCase("asc", order)) {
                    orderItemList.add(OrderItem.asc(column));
                } else if (StringUtils.equalsIgnoreCase("desc", order)) {
                    orderItemList.add(OrderItem.desc(column));
                }
            }
        }
        return orderItemList;
    }

    public void handleQueryWrapper(BaseBean baseEntity, AbstractWrapper queryWrapper, Class modelClass, String... excludeFields) {
        List<Field> reqFields = ReflectionUtils.getAllFields(baseEntity);
        Map<String, Field> modelFieldsMap = getFieldMap(modelClass);
        Map<String, String> joinTableSqlMap = new HashMap<>();
        for (Field reqField : reqFields) {
            reqField.setAccessible(true);
            String fieldName = reqField.getName();
            if (excludeFields != null && Arrays.asList(excludeFields).contains(fieldName)) {
                continue;
            }
            QueryCondition queryCondition = reqField.getAnnotation(QueryCondition.class);
            if (modelFieldsMap.get(fieldName) == null && !checkFieldAnnotation(reqField) && queryCondition == null) {
                continue;
            }
            IgnoreQuery ignoreQuery = reqField.getAnnotation(IgnoreQuery.class);
            if (ignoreQuery != null) {
                continue;
            }

            try {
                Object val = reqField.get(baseEntity);
                String column = getColumn(fieldName);
                column = handleJoinAndHandleColumn(queryWrapper, joinTableSqlMap, queryCondition, column);
                if (val != null) {
                    if (queryCondition != null && !QueryEnum.AUTO.equals(queryCondition.value())) {
                        setQueryByCondition(queryWrapper, column, val, queryCondition);
                    } else {
                        if (val instanceof String) {
                            queryWrapper.like(StringUtils.isNotBlank((CharSequence) val), column, val);
                        } else if (val instanceof Integer || val instanceof Long || val instanceof Boolean || val instanceof Byte || val instanceof Short) {
                            queryWrapper.eq(val != null, column, val);
                        } else if (val instanceof Date) {
                            TimeBegin timeBegin = reqField.getAnnotation(TimeBegin.class);
                            TimeEnd timeEnd = reqField.getAnnotation(TimeEnd.class);
                            if (timeBegin != null && StringUtils.isNotBlank(timeBegin.value())) {
                                String timeField = handleJoinAndHandleColumn(queryWrapper, joinTableSqlMap, null, getColumn(timeBegin.value()));
                                queryWrapper.ge(timeField, val);
                            } else if (timeEnd != null && StringUtils.isNotBlank(timeEnd.value())) {
                                String timeField = handleJoinAndHandleColumn(queryWrapper, joinTableSqlMap, null, getColumn(timeEnd.value()));
                                queryWrapper.lt(timeField, val);
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleJoinAndHandleColumn(AbstractWrapper queryWrapper, Map<String, String> joinTableSqlMap, QueryCondition queryCondition, String column) {
        if (queryWrapper instanceof MPJQueryWrapper) {
            if (queryCondition != null) {
                JoinCondition joinCondition = queryCondition.leftJoin();
                String table = joinCondition.table();
                String field = joinCondition.field();
                String joinField = joinCondition.joinField();
                if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(joinField) && StringUtils.isNotBlank(field)) {
                    String asColumn = StringUtils.isNotBlank(joinCondition.asResp()) ? joinCondition.asResp() : column;
                    String joinFieldName = getColumn(joinField);
                    String aFieldName = getColumn(field);
                    String aColumn = StringUtils.isNotBlank(joinCondition.value()) ? getColumn(joinCondition.value()) : column;
                    column = table + "." + aColumn;
                    ((MPJQueryWrapper<?>) queryWrapper).select(column + " as " + asColumn);
                    String joinSql = getValidTable(table) + " as " + table + " on " + table + "." + aFieldName + " = t." + joinFieldName;
                    if (joinTableSqlMap.get(table) == null) {
                        joinTableSqlMap.put(table, joinSql);
                        ((MPJQueryWrapper<?>) queryWrapper).leftJoin(joinSql);
                    }
                } else {
                    column = "t." + column;
                }
            } else {
                column = "t." + column;
            }
        }
        return column;
    }

    private String getColumn(String fieldName) {
        return com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(fieldName);
    }

    private String getValidTable(String tableName) {
        return com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(tableName);
    }

    private void setQueryByCondition(AbstractWrapper queryWrapper, String column, Object val, QueryCondition queryCondition) {
        QueryEnum queryEnum = queryCondition.value();
        setQueryWrapper(queryWrapper, column, val, queryCondition, queryEnum);
    }

    private void setQueryWrapper(AbstractWrapper queryWrapper, String column, Object val, QueryCondition queryCondition, QueryEnum queryEnum) {
        switch (queryEnum) {
            case EQ:
                if (val instanceof String) {
                    queryWrapper.eq(StringUtils.isNotBlank((CharSequence) val), column, val);
                } else if (val instanceof Integer || val instanceof Long || val instanceof Boolean || val instanceof Byte || val instanceof Short) {
                    queryWrapper.eq(column, val);
                }
                break;
            case NQ:
                if (val instanceof String) {
                    queryWrapper.ne(StringUtils.isNotBlank((CharSequence) val), column, val);
                } else if (val instanceof Integer || val instanceof Long || val instanceof Boolean || val instanceof Byte || val instanceof Short) {
                    queryWrapper.ne(column, val);
                }
                break;
            case IN:
                if (val instanceof String) {
                    List<?> valList = MyStringUtils.toList((String) val, queryCondition.InClassType());
                    queryWrapper.in(StringUtils.isNotBlank((CharSequence) val), column, valList);
                }
                break;
            case LIKE_LEFT:
                if (val instanceof String) {
                    queryWrapper.likeLeft(StringUtils.isNotBlank((CharSequence) val), column, val);
                } else if (val instanceof Integer || val instanceof Long || val instanceof Boolean || val instanceof Byte || val instanceof Short) {
                    queryWrapper.likeLeft(column, val);
                }
                break;
            case LIKE_RIGHT:
                if (val instanceof String) {
                    queryWrapper.likeRight(StringUtils.isNotBlank((CharSequence) val), column, val);
                } else if (val instanceof Integer || val instanceof Long || val instanceof Boolean || val instanceof Byte || val instanceof Short) {
                    queryWrapper.likeRight(column, val);
                }
                break;
            case LIKE:
                if (val instanceof String) {
                    queryWrapper.like(StringUtils.isNotBlank((CharSequence) val), column, val);
                } else if (val instanceof Integer || val instanceof Long || val instanceof Boolean || val instanceof Byte || val instanceof Short) {
                    queryWrapper.like(column, val);
                }
                break;
            case AND_OR:
                if (val instanceof String) {
                    if (queryCondition == null) {
                        throw new RuntimeException("条件查询错误，出现循环andOR");
                    }
                    AndOrCondition andOrCondition = queryCondition.andOr();
                    if (andOrCondition != null && StringUtils.isNotBlank((CharSequence) val)) {
                        String delimiter = andOrCondition.delimiter();
                        if (andOrCondition.multiValue()) {
                            //这种情况是一个字段里面存多个值，且以,xx,xx,分割
                            Consumer<AbstractWrapper> likeOrWrapper = wrapper -> {
                                String[] split = StringUtils.split((String) val, delimiter);
                                for (String itemVal : split) {
                                    wrapper.like(StringUtils.isNotBlank(itemVal), column, delimiter + itemVal + delimiter).or();
                                }
                            };
                            queryWrapper.and(likeOrWrapper);
                        } else {
                            //这种情况是多个字段查询一个值
                            String[] fields = andOrCondition.fields();
                            if (ArrayUtils.isEmpty(fields)) {
                                return;
                            }
                            QueryEnum value = andOrCondition.value();
                            if (value != null && !QueryEnum.AUTO.equals(value)) {

                                Consumer<AbstractWrapper> likeOrWrapper = wrapper -> {
                                    if (column.startsWith("t.")) {
                                        for (String field : fields) {
                                            setQueryWrapper(wrapper, "t." + getColumn(field), val, null, value);
                                        }
                                    } else {
                                        for (String field : fields) {
                                            setQueryWrapper(wrapper, getColumn(field), val, null, value);
                                        }
                                    }
                                };
                                queryWrapper.and(likeOrWrapper);
                            } else {
                                //默认查询的情况
                                Consumer<AbstractWrapper> likeOrWrapper = wrapper -> {
                                    if (column.startsWith("t.")) {
                                        for (String field : fields) {
                                            wrapper.like(true, "t." + getColumn(field), val).or();
                                        }
                                    } else {
                                        for (String field : fields) {
                                            wrapper.like(true, getColumn(field), val).or();
                                        }
                                    }
                                };
                                queryWrapper.and(likeOrWrapper);
                            }
                        }
                    }

                }
                break;
        }
    }

    private boolean checkFieldAnnotation(Field reqField) {
        TimeBegin timeBegin = reqField.getAnnotation(TimeBegin.class);
        TimeEnd timeEnd = reqField.getAnnotation(TimeEnd.class);
        if (timeBegin != null || timeEnd != null) {
            return true;
        }
        return false;
    }

    public Map<String, Field> getFieldMap(Class clazz) {
        List<Field> modelFields = ReflectionUtils.getAllFields(clazz);
        Map<String, Field> modelFieldsMap = new HashMap<>();
        for (Field modelField : modelFields) {
            modelFieldsMap.put(modelField.getName(), modelField);
        }
        return modelFieldsMap;
    }
}
