package com.javagpt.file.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileStorageTypeEnum {

    BAIDU(1);

    private final Integer type;
}
