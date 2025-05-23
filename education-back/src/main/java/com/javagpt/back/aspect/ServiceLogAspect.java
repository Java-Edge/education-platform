package com.javagpt.back.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.javagpt.back.util.UserContextHolder;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@Aspect
public class ServiceLogAspect {

    @Resource
    private ApplicationContext applicationContext;

    private static final RateLimiter rateLimiter = RateLimiter.create(500);
    private static final List<Integer> devUserIds = Lists.newArrayList(5, 6, 21, 185);


    private static final List<String> articles = Lists.newArrayList(
            "TimeLineController",
            "DownloadController");

    @Pointcut("execution(* com.javagpt.back.service.*.*(..))")
    public void pointCut(){

    }

    /**
     * 对 controller 限流
     */
    @Pointcut("execution(* com.javagpt.back.controller.*.*(..))")
    public void rateLimitPointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null){
            return;
        }
        HttpServletRequest request =attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

//        log.info("------------- 开始 -------------");
        log.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
//        log.info("类名方法: {}.{}", signature.getDeclaringTypeName(), name);
//        log.info("远程地址: {}", request.getRemoteAddr());
//        log.info("IP地址: {}", IpUtils.getIpAddress(request));


        Object[] args = joinPoint.getArgs();
        Object[] arguments  = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }

        String[] excludeProperties = {"password", "file"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        // 不打空arguments
        if (arguments.length != 0) {
            log.info("请求参数: {}", JSONObject.toJSONString(arguments, excludeFilter));
        }
    }

    @SneakyThrows // 使用之后不需要抛出异常，lombok会自动在编译时加上try/catch
    @Around("rateLimitPointCut()")
    public Object rateLimit(ProceedingJoinPoint joinPoint) {
        if (rateLimiter.tryAcquire()) {
            return joinPoint.proceed();
        } else {
            log.error("Rate limit exceeded");
            return ResultBody.error("访问太过频繁");
        }
    }

    @Before("execution(* com.javagpt.back.controller.*.getById(..))")
    public void calPageView(JoinPoint joinPoint) {
//        log.info("Method called: " + joinPoint.getSignature().toShortString());

        Object[] args = joinPoint.getArgs();
        if (args.length == 0 || (!(args[0] instanceof Number) && !(args[0] instanceof String))) {
            log.error("Invalid arguments. Expected at least one argument of type Integer.");
            return;
        }

        // 提取 entityId 和 mapper 信息
        Long entityId = getEntityId(args[0]);
        String controllerClassName = joinPoint.getTarget().getClass().getSimpleName();
        if (controllerClassName.equals("DownloadController")) {
            return;
        }
        // 获取 entity 类名
        String entityMapperName = "com.javagpt.back.mapper." + controllerClassName.replace("Controller", "Mapper");
        if (articles.contains(controllerClassName)) {
            entityMapperName = "com.javagpt.back.mapper.ArticleMapper";
        } else {
            entityMapperName = "com.javagpt.back.mapper." + controllerClassName.replace("Controller", "Mapper");
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null){
            return;
        }
        HttpServletRequest request =attributes.getRequest();
        Integer userId = UserContextHolder.getCurrentUserId(request);
        if (userId != null && devUserIds.contains(userId)) {
            // 排除开发人员的调试用户 id,不计入 pv
            return;
        }
        try {
            Class<?> entityMapperClass = Class.forName(entityMapperName);
            BaseMapper<Object> entityMapper = (BaseMapper<Object>) applicationContext.getBean(entityMapperClass);

            // 获取 entity
            Object entityFromDB = entityMapper.selectById(entityId);

            // 更新pageView
            Method getPageViewMethod = entityFromDB.getClass().getMethod("getPageView");
            Method setPageViewMethod = entityFromDB.getClass().getMethod("setPageView", Integer.class);

            Integer pageView = (Integer) getPageViewMethod.invoke(entityFromDB);
            if (pageView == null) {
                pageView = 0;
            }
            setPageViewMethod.invoke(entityFromDB, pageView + 1);

            // 更新entity
            entityMapper.updateById(entityFromDB);

//            log.info(String.format("[%s]实体被访问了[%s]次.", entityId, pageView));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("Error in calPageView: " + e.getMessage(), e);
        }
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = point.proceed();

        String[] excludeProperties = {"password", "file"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
        excludeFilter.addExcludes(excludeProperties);
        if (Objects.isNull(result)) {
            log.error("返回结果为null");
        }
        long cost = System.currentTimeMillis() - startTime;
        if (cost > 200) {
            log.warn("------------- {} 结束 耗时：{} ms -------------", point.getSignature(), cost);
        }
        return result;
    }

    private Long getEntityId(Object arg) {
        if (arg instanceof Long l) {
            return  l;
        } else if (arg instanceof Integer i) {
            return Long.valueOf(i);
        } else {
            return Long.parseLong((String) arg);
        }
    }
}