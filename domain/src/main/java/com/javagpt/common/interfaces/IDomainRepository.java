package com.javagpt.common.interfaces;


import com.javagpt.common.repository.IBaseRepository;
import com.javagpt.common.req.BaseBean;

public interface IDomainRepository<DO extends BaseBean> {

    IBaseRepository<DO> repository();
}
