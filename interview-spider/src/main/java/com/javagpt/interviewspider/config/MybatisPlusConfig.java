package com.javagpt.interviewspider.config;

import org.springframework.context.annotation.Configuration;


/**
 * @Author 徐望成
 * @Date 2023/7/26 12:28
 * @PackageName:com.javagpt.interviewspider.config
 * @ClassName: MybatisPlusConfig
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
