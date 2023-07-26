package com.javagpt.interviewspider.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * All rights Reserved, Designed By www.tom.com
 *
 * @Author 徐望成
 * @Date 2023/7/26 12:28
 * @PackageName:com.javagpt.interviewspider.config
 * @ClassName: MybatisPlusConfig
 * @Description: TODO
 * @Copyright: 2019 www.tomonline-inc.com Inc. All rights reserved.
 * 注意：本内容仅限于TOM集团内部传阅，禁止外泄以及用于其他的商业目
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfig {

//
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//        mapperScannerConfigurer.setBasePackage(MAPPER_PACKAGE);
//        //配置通用Mapper，详情请查阅官方文档
//        Properties properties = new Properties();
//        properties.setProperty("mappers", MAPPER_INTERFACE_REFERENCE);
//        properties.setProperty("notEmpty", "true");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
//        properties.setProperty("IDENTITY", "SELECT UUID()");//使用UUID作為主鍵
//        properties.setProperty("ORDER","BEFORE");//将查询主键作为前置操作
//        mapperScannerConfigurer.setProperties(properties);
//        return mapperScannerConfigurer;
//    }

}
