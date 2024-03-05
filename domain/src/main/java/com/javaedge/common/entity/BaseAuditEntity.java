package com.javaedge.common.entity;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.javaedge.common.factory.RepositoryFactory;
import com.javaedge.common.interfaces.IDomainCRUD;
import com.javaedge.common.interfaces.IDomainRepository;
import com.javaedge.common.repository.IBaseRepository;
import com.javagpt.common.exception.BusinessRuntimeException;
import com.javagpt.common.req.BaseAuditBean;
import com.javagpt.common.req.BaseBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.function.Function;

public abstract class BaseAuditEntity<Entity extends BaseBean, R extends IBaseRepository<Entity>> extends BaseAuditBean implements IDomainCRUD<Entity>, IDomainRepository<Entity> {

    protected Class<R> repositoryClass = this.currentRepositoryClass();


    protected Class<R> currentRepositoryClass() {
        return (Class<R>) ReflectionKit.getSuperClassGenericType(getClass(), Object.class, 1);
    }

    @Override
    public void delete() {
        Objects.requireNonNull(getId());
        repository().deleteById(getId());
    }

    @Override
    public Entity fetchOne() {
        Objects.requireNonNull(getId());
        BaseBean byId = repository().findById(getId());
        fullFillThis(byId);
        return (Entity) byId;
    }

    protected void fullFillThis(BaseBean bean) {
        BeanUtils.copyProperties(bean, this);
    }


    @Override
    public Entity save() {
        Entity aEntity = repository().saveBean((Entity) this);
        fullFillThis(aEntity);
        return aEntity;
    }

    @Override
    public Entity saveOrUpdate() {
        if (Objects.isNull(getId())) {
            return save();
        } else {
            return update();
        }
    }

    @Override
    public Entity update() {
        checkId();
        updateCheck();
        clearUpdate();
        repository().updateBean((Entity) this);
        return null;
    }

    public void updateCheck() {

    }

    protected void checkUpdateUnique(String ukCode, String errorMsg, Function<String, Entity> function) {
        if (StringUtils.isNotBlank(ukCode)) {
            Entity db = function.apply(ukCode);
            if (db != null && !db.getId().equals(this.getId())) {
                throw BusinessRuntimeException.error(errorMsg);
            }
        }
    }

    protected void checkId() {
        Objects.requireNonNull(this.getId(), "更新的id不能为空");
    }

    public Entity clearUpdate() {
        this.setUpdateTime(null);
        this.setUpdateBy(null);
        this.setUpdateById(null);
        return (Entity) this;
    }

    @Override
    public R repository() {
        return RepositoryFactory.get(repositoryClass);
    }

}
