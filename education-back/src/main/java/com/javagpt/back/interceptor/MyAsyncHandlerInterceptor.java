package com.javagpt.back.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Service
public class MyAsyncHandlerInterceptor implements AsyncHandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("interceptor#preHandle called.");
        return true;

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        log.info("interceptor#postHandle called. ");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("interceptor#afterCompletion called.");
    }

    /**
     * 该方法执行后，会执行Controller方法返回的callable方法
     * 这个方法的目的时，当servlet线程被释放后，执行清除例如ThreadLocal、MDC等资源的操作。
     */
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("interceptor#afterConcurrentHandlingStarted. ");
    }
}
