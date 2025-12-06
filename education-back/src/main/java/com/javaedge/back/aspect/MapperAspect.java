package com.javaedge.back.aspect;

import com.javaedge.common.req.PageQueryParam;
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

    @Pointcut("execution(* com.javaedge.back.mapper.*.*(..))")
    public void mapperPointCut() {

    }

    @Before("mapperPointCut()")
    public void before(JoinPoint joinPoint) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();

            // 获取分页参数
            String pageNoHeader = request.getHeader("pageNo");
            String pageSizeHeader = request.getHeader("pageSize");

            // 验证分页参数是否为正数
            if (pageNoHeader != null && pageSizeHeader != null) {
                int pageNo = Integer.parseInt(pageNoHeader);
                int pageSize = Integer.parseInt(pageSizeHeader);
                if (pageSize > 20) {
                    throw new IllegalArgumentException("Page size exceeds the maximum limit");
                }
                if (pageNo > 0 && pageSize > 0) {
                    Object[] args = joinPoint.getArgs();
                    Object[] arguments = args.clone(); // 使用clone简化参数复制

                    // 遍历参数并设置PageQueryParam
                    for (int i = 0; i < args.length; i++) {
                        Object arg = args[i];
                        if (arg instanceof PageQueryParam) {
                            PageQueryParam pageQueryParam = (PageQueryParam) arg;
                            pageQueryParam.setPageNo(pageNo);
                            pageQueryParam.setPageSize(pageSize);
                            arguments[i] = pageQueryParam; // 更新参数数组
                        }
                    }
                    // 这里可以添加逻辑来处理更新后的参数数组，如果需要的话
                }
            }
        } catch (Exception e) {
            log.error("Invalid page number or page size", e);
        }
    }
}