package com.javagpt.back.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author JavaEdge
 * @date 2024/1/11
 */
@Slf4j
public class TraceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try {
            String traceId = MDC.get(LogConstants.MDC_LOG_TRACE_ID_KEY);
            if (StringUtils.isBlank(traceId)) {
                MDC.put(LogConstants.MDC_LOG_TRACE_ID_KEY, TraceUtils.getTraceId());
            }
        } catch (RuntimeException e) {
            log.error("mvc自定义log跟踪拦截器执行异常", e);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        try {
            MDC.clear();
        } catch (RuntimeException ex) {
            log.error("mvc自定义log跟踪拦截器执行异常", ex);
        }
    }
}
