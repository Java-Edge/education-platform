package com.javagpt.user.entity;

import com.icv.monitorplatform.common.exception.BusinessRuntimeException;
import com.icv.monitorplatform.domain.common.entity.BaseAuditEntity;
import com.icv.monitorplatform.domain.user.constant.UserStatusEnum;
import com.icv.monitorplatform.domain.user.constant.UserTypeEnum;
import com.icv.monitorplatform.domain.user.resporitory.UserRepository;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

/**
 * @author JavaEdge
 */
@Data
@Accessors(chain = true)
public class UserEntity extends BaseAuditEntity<UserEntity, UserRepository> {

    private final static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * 企业主键id
     */
    private Long enterpriseId;

    /**
     * 用户类型 1、企业账户 2、普通账户
     */
    private Integer type;

    /**
     * 账号名称
     */
    private String account;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别 1、男 2、女
     */
    private Integer sex;

    /**
     * 状态 1、审核中  2、启用  3、禁用
     */
    private Integer status;

    @Override
    public UserEntity fetchOne() {
        UserEntity userEntity = null;
        if (getId() != null) {
            userEntity = repository().findById(this.getId());
        } else if (StringUtils.isNotBlank(account)) {
            userEntity = repository().findByAccount(account);
        } else {
            throw BusinessRuntimeException.error("无效的参数");
        }
        fullFillThis(userEntity);
        return this;
    }

    @Override
    public UserEntity save() {
        UserEntity db = repository().findByAccount(account);
        if (db != null) {
            throw BusinessRuntimeException.error("账号已存在");
        }
        db = repository().findByEmail(email);
        if (db != null) {
            throw BusinessRuntimeException.error("邮箱已存在");
        }
        return repository().saveBean(this);
    }

    public UserEntity createNormalUser() {
        setType(UserTypeEnum.NORMAL.getType());
        setStatus(UserStatusEnum.ENABLED.getStatus());
        password = bCryptPasswordEncoder.encode(password);
        return save();
    }

    public UserEntity createEnterpriseUser() {
        setType(UserTypeEnum.ENTERPRISE.getType());
        setStatus(UserStatusEnum.AUDITING.getStatus());
        password = bCryptPasswordEncoder.encode(password);
        return save();
    }


    @Override
    public UserEntity update() {
        Objects.requireNonNull(this.getId(), "id不能为空");
        if (StringUtils.isBlank(password)) {
            setPassword(null);
        } else {
            password = bCryptPasswordEncoder.encode(password);
        }
        this.setAccount(null);
        if (StringUtils.isNotBlank(this.getAccount())) {
            UserEntity db = repository().findByAccount(account);
            if (db != null && !db.getId().equals(this.getId())) {
                throw BusinessRuntimeException.error("账号已存在");
            }
        }
        if (StringUtils.isNotBlank(email)) {
            UserEntity byEmail = repository().findByEmail(email);
            if (byEmail != null && !byEmail.getId().equals(this.getId())) {
                throw BusinessRuntimeException.error("邮箱已存在");
            }
        }
        repository().updateBean(this);
        return null;
    }


}