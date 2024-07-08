package com.javagpt.back.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.PermissionConfig;
import com.javagpt.back.interceptor.CheckTokenInterceptor;
import com.javagpt.back.interceptor.FuseInterceptor;
import com.javagpt.back.interceptor.MyAsyncHandlerInterceptor;
import com.javagpt.back.service.PermissionConfigService;
import com.javagpt.common.enums.PermissionConfigTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;

    @Autowired
    private PermissionConfigService permissionConfigService;

    @Autowired
    private MyAsyncHandlerInterceptor myAsyncHandlerInterceptor;

    @Autowired
    private FuseInterceptor fuseInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        QueryWrapper<PermissionConfig> qw = new QueryWrapper<>();
        qw.eq("type", PermissionConfigTypeEnum.NEED_LOGIN.getResultCode());
        qw.eq("status", 1);
        List<String> paths = permissionConfigService.list(qw).stream().map(PermissionConfig::getPath).collect(Collectors.toList());
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns(paths);

//        registry.addInterceptor(myAsyncHandlerInterceptor)
//                .addPathPatterns("/**");

        registry.addInterceptor(fuseInterceptor)
                .addPathPatterns("/**");
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5);
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        threadPoolTaskExecutor.setMaxPoolSize(5);
        threadPoolTaskExecutor.setQueueCapacity(50);
        threadPoolTaskExecutor.setThreadNamePrefix("async-service-");
        threadPoolTaskExecutor.initialize();

        configurer.setTaskExecutor(threadPoolTaskExecutor);
    }
}
