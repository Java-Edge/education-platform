package com.javagpt.interviewspider.dto;

import com.javagpt.interviewspider.entity.CareerEntity;
import lombok.Data;

import java.util.List;

/**
 * All rights Reserved, Designed By www.tom.com
 *
 * @Author 徐望成
 * @Date 2023/7/25 16:24
 * @PackageName:com.javagpt.interviewspider.dto
 * @ClassName: Result
 * @Description: TODO
 * @Copyright: 2019 www.tomonline-inc.com Inc. All rights reserved.
 * 注意：本内容仅限于TOM集团内部传阅，禁止外泄以及用于其他的商业目
 * @Version 1.0
 */
@Data
public class Result<T> {

    private List<T> result;

}
