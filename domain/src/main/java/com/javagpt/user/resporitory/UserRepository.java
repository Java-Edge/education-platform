package com.javagpt.user.resporitory;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.icv.monitorplatform.domain.common.repository.IBaseRepository;
import com.icv.monitorplatform.domain.rbac.vo.UserEnterpriseRolePageVo;
import com.icv.monitorplatform.domain.user.entity.UserEntity;

import java.util.List;

/**
 * @author JavaEdge
 * @date 2023/3/15
 */
public interface UserRepository extends IBaseRepository<UserEntity> {
    /**
     * 根据账号查询用户
     *
     * @param account
     * @return
     */
    UserEntity findByAccount(String account);

    /**
     * 根据邮箱查用户
     *
     * @param email
     * @return
     */
    UserEntity findByEmail(String email);

    List<UserEntity> findByEnterpriseIdAndType(Long enterpriseId, Integer type);


    <T> IPage<T> pageWithEnterpriseRole(UserEnterpriseRolePageVo param, Class<T> tClass);

    void resetPassword(Long id, String password);
}
