package com.javaedge.common.interfaces;

import com.javaedge.common.repository.IBaseRepository;
import com.javaedge.common.req.BaseBean;

public interface IDomainRepository<DO extends BaseBean> {

    IBaseRepository<DO> repository();
}
