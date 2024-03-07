package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javagpt.infra.mysql.po.common.BasePO;
import lombok.Data;

@TableName(value = "user")
@Data
public class UserPO extends BasePO {

    private String username;

    private String password;

    private Integer status;
}