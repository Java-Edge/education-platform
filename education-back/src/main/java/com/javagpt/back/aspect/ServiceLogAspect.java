package com.javagpt.back.aspect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.google.common.util.concurrent.RateLimiter;
import com.javagpt.common.resp.ResultBody;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Aspect
public class ServiceLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(com.javagpt.back.aspect.ServiceLogAspect.class);

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
        String now = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(new Date());
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." +joinPoint.getSignature().getName();
        logger.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));
    }

    private static final RateLimiter rateLimiter = RateLimiter.create(500);

    @SneakyThrows // 使用之后不需要抛出异常，lombok会自动在编译时加上try/catch
    @Around("rateLimitPointCut()")
    public Object rateLimit(ProceedingJoinPoint joinPoint) {
        logger.info("当前限流器速率：" + rateLimiter.getRate());
        if (rateLimiter.tryAcquire()) {
            return joinPoint.proceed();
        } else {
            // 如果超出限流次数
            ResultBody resultBody = ResultBody.error("访问太过频繁");
            return resultBody;
        }
    }

    // 计算文章的浏览量
    @Before("execution(* com.javagpt.back.controller.*.getById(..))")
    public void calPageView(JoinPoint joinPoint) {
        // Log the method and target class information
        logger.info("Method called: " + joinPoint.getSignature().toShortString());
        logger.info("Target class: " + joinPoint.getTarget().getClass().getName());

        // Extract arguments
        Object[] args = joinPoint.getArgs();
        if (args.length == 0 || !(args[0] instanceof Integer)) {
            logger.error("Invalid arguments. Expected at least one argument of type Integer.");
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

            logger.info(String.format("[%s]实体被访问了[%s]次.", entityId, pageView));
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            logger.error("Error in calPageView: " + e.getMessage(), e);
        }
    }

}














