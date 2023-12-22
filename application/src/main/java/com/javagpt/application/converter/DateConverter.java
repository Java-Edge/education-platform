package com.javagpt.application.converter;

import cn.hutool.core.date.DateUtil;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * 在领域驱动设计（DDD）架构中，通常将转换器（converter）工具类定义在领域层（Domain Layer）或应用层（Application Layer）中。这两个层次通常是包含业务逻辑和用例执行的地方。
 *
 * 1. **领域层（Domain Layer）**：领域层主要包含领域模型、实体、值对象等业务相关的概念。在这一层次，转换器用于将领域模型与其他数据表达形式进行转换。这包括将领域对象转换为持久化模型，或者与外部系统交互时的数据格式转换。
 *
 * 2. **应用层（Application Layer）**：应用层包含应用服务、用例等，负责协调领域对象的使用，执行业务用例。在应用层，转换器工具类可以用于将领域对象与用户界面之间的数据进行转换，或者在应用服务之间进行数据传递。
 *
 * 这样的设计有助于保持领域层的纯净性，因为领域层应该专注于业务逻辑而不涉及外部表现或持久化细节。转换器的责任是处理不同层次之间的数据格式转换，确保各层之间的解耦。
 *
 * 总的来说，具体的设计取决于项目的需求和架构。有些情况下，可能会在应用层定义转换器，而在领域层专注于业务逻辑。
 * controller接收string类型转换为日期类
 * 将标准日期、标准日期时间、时间戳转换成Date类型
 */
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String value) {

        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return DateUtil.parse(value);
        } catch (Exception e) {
            throw new ValidationException("无效的日期格式");
        }
    }
}
