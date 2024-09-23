package com.javagpt.back.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.javagpt.infra.mysql.po.common.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "user")
@Data
@NoArgsConstructor
public class UserPO extends BasePO {

    private String username;

    private String password;

    private Integer status;

    public UserPO(String user1, int i) {
        this.username = user1;
        this.status = i;
    }

}