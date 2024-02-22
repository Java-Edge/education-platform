//package com.javagpt.back.config;
//
//import com.fasterxml.classmate.TypeResolver;
//import com.javagpt.common.constant.Constant;
//import com.javagpt.common.util.UrlUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.context.WebServerInitializedEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.ResponseBody;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * @author JavaEdge
// * @date 2024/2/2
// */
//@Configuration
//@EnableSwagger2WebMvc
//public class SwaggerConfig implements ApplicationListener<WebServerInitializedEvent> {
//    /**
//     * 可以通过变量设置swagger-ui是否显示，比如测试环境可以暴露api文档，生产环境我们就关闭
//     */
//    @Value("${swagger.enable:true}")
//    private boolean enableSwagger;
//
//    @Value("${server.servlet.context-path:}")
//    private String contextPath;
//
//    @Bean
//    public Docket webApiConfig() {
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("API")
//                .enable(enableSwagger)
//                .additionalModels(new TypeResolver().resolve(ResponseBody.class))
//                .apiInfo(webApiInfo())
//                //分组名称
//                .groupName("API/V1")
//                .select()
//                //这里指定Controller扫描包路径
//                .apis(RequestHandlerSelectors.any())
//                .paths(path -> path != null && (path.startsWith(UrlUtils.appendUri(contextPath, Constant.API_V1))))
//                .build()
//                .globalOperationParameters(operationParameters());
//
//    }
//
//    private static List<Parameter> operationParameters() {
//        return Collections.singletonList(
//                new ParameterBuilder()
//                        .name("token")
//                        .description("登录token")
//                        .modelRef(new ModelRef("string"))
//                        .parameterType("header")
//                        .required(true)
//                        .build()
//        );
//    }
//
//    private ApiInfo webApiInfo() {
//        return new ApiInfoBuilder()
//                .contact(new Contact("issac.hu", "", ""))
//                .title("API文档")
//                .description("API文档")
//                .version("1.0")
//                .build();
//    }
//
//    @Override
//    public void onApplicationEvent(WebServerInitializedEvent event) {
//        System.out.println("swagger访问地址:http://localhost:8082" + contextPath + "/doc.html");
//    }
//}