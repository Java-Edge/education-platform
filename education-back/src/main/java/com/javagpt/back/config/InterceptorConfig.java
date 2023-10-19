package com.javagpt.back.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.javagpt.back.entity.PermissionConfig;
import com.javagpt.back.entity.PermissionConfigTypeEnum;
import com.javagpt.back.interceptor.CheckTokenInterceptor;
import com.javagpt.back.service.PermissionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        QueryWrapper<PermissionConfig> qw = new QueryWrapper<>();
        qw.eq("type", PermissionConfigTypeEnum.NEED_LOGIN.getResultCode());
        qw.eq("status", 1);
        List<String> paths = permissionConfigService.list(qw).stream().map(PermissionConfig::getPath).collect(Collectors.toList());
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns(paths);
    }
}
