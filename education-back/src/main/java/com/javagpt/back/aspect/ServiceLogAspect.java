package com.javagpt.back.aspect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.util.concurrent.RateLimiter;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.common.util.IpUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
@Component
@Aspect
public class ServiceLogAspect {

    @Resource
    private ApplicationContext applicationContext;

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
        String ip = request.getRemoteHost();
        String ip2 = IpUtils.getIpAddress(request);
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." +joinPoint.getSignature().getName();
        log.info(String.format("用户[%s] 访问了[%s]. ip2:%s", ip, target, ip2));
    }

    private static final RateLimiter rateLimiter = RateLimiter.create(500);

    @SneakyThrows // 使用之后不需要抛出异常，lombok会自动在编译时加上try/catch
    @Around("rateLimitPointCut()")
    public Object rateLimit(ProceedingJoinPoint joinPoint) {
        log.info("当前限流器速率：" + rateLimiter.getRate());
        if (rateLimiter.tryAcquire()) {
            return joinPoint.proceed();
        } else {
            // 如果超出限流次数
            return ResultBody.error("访问太过频繁");
        }
    }

    // 计算文章的浏览量
    @Before("execution(* com.javagpt.back.controller.*.getById(..))")
    public void calPageView(JoinPoint joinPoint) {

        log.info("Method called: " + joinPoint.getSignature().toShortString());
        log.info("Target class: " + joinPoint.getTarget().getClass().getName());

        // Extract arguments
        Object[] args = joinPoint.getArgs();
        if (args.length == 0 || !(args[0] instanceof Integer)) {
            log.error("Invalid arguments. Expected at least one argument of type Integer.");
            return;
        }

        // Extract entityId and mapper information
        Integer entityId = (Integer) args[0];
        String controllerClassName = joinPoint.getTarget().getClass().getSimpleName();
        String entityMapperName = "com.javagpt.back.mapper." + controllerClassName.replace("Controller", "Mapper");

        try {
            Class<?> entityMapperClass = Class.forName(entityMapperName);
            BaseMapper<Object> entityMapper = (BaseMapper<Object>) applicationContext.getBean(entityMapperClass);

            // Retrieve the entity from the database
            Object entityFromDB = entityMapper.selectById(entityId);

            // Update the pageView
            Method getPageViewMethod = entityFromDB.getClass().getMethod("getPageView");
            Method setPageViewMethod = entityFromDB.getClass().getMethod("setPageView", Integer.class);

            Integer pageView = (Integer) getPageViewMethod.invoke(entityFromDB);
            setPageViewMethod.invoke(entityFromDB, pageView + 1);

            // Update the entity
            entityMapper.updateById(entityFromDB);

            log.info(String.format("[%s]实体被访问了[%s]次.", entityId, pageView));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            log.error("Error in calPageView: " + e.getMessage(), e);
        }
    }

}














