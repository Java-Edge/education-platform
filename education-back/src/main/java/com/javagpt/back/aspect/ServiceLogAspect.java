package com.javagpt.back.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.util.concurrent.RateLimiter;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.entity.ArticleEntity;
import com.javagpt.back.mapper.ArticleMapper;
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
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Aspect
public class ServiceLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(com.javagpt.back.aspect.ServiceLogAspect.class);

    @Resource
    private ArticleMapper articleMapper;

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
        double rate = rateLimiter.getRate();
        System.out.println(rate);
        if (rateLimiter.tryAcquire()) {
            return joinPoint.proceed();
        } else {
            // 如果超出限流次数
            ResultBody resultBody = ResultBody.error("访问太过频繁");
            return resultBody;
        }
    }

    // 计算文章的浏览量
    @Before("execution(* com.javagpt.back.controller.ArticleController.getById(..))")
    public void calPageView(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Integer articleId = (Integer) args[0];
        QueryWrapper<ArticleEntity> qw = new QueryWrapper<>();
        qw.eq("article_id", articleId);
        ArticleEntity articleEntity = articleMapper.selectById(articleId);
        articleEntity.setPageView(articleEntity.getPageView()+1);
        articleMapper.updateById(articleEntity);
        logger.info(String.format("[%s]文章被访问了[%s]次.", articleId, articleEntity.getPageView()));
    }

}














