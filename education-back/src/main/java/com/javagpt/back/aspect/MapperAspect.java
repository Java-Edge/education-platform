package com.javagpt.back.aspect;

import com.javagpt.common.req.PageQueryParam;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@Aspect
public class MapperAspect {

    @Pointcut("execution(* com.javagpt.back.mapper.*.*(..))")
    public void mapperPointCut(){

    }

    @Before("mapperPointCut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null){
            return;
        }
        HttpServletRequest request =attributes.getRequest();
        int pageNo = Integer.parseInt(request.getHeader("pageNo"));
        int pageSize = Integer.parseInt(request.getHeader("pageSize"));
        Object[] args = joinPoint.getArgs();
        Object[] arguments  = new Object[args.length];
        for (Object arg : args) {
            if (arg instanceof PageQueryParam) {
                PageQueryParam pageQueryParam = (PageQueryParam) arg;
                pageQueryParam.setPageNo(pageNo);
                pageQueryParam.setPageSize(pageSize);
                arguments[args.length - 1] = pageQueryParam;
            }
        }
    }
}