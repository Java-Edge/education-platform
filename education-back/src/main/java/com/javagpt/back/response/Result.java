package com.javagpt.back.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 统一响应对象
 *
 * @PackageName:com.javagpt.background.result
 * @ClassName:Result
 * @Description:
 * @author:何进业
 * @date:2021/5/24 16:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T>{

    private int code;

    private String message;

    private T data;
}
