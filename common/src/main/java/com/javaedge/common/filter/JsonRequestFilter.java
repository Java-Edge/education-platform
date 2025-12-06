package com.javaedge.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.javaedge.common.constant.CommonConstants.CONTENT_TYPE_JSON;

/**
 * @author JavaEdge
 */
@Slf4j
public class JsonRequestFilter implements Filter {

    private static final Set<String> INCLUDE_METHODS = new HashSet<>();

    static {
        INCLUDE_METHODS.add("POST");
        INCLUDE_METHODS.add("PUT");
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            if (request instanceof HttpServletRequest) {
                String contentType = request.getContentType();
                if (StringUtils.isNotBlank(contentType) && contentType.contains(CONTENT_TYPE_JSON)) {
                    String method = ((HttpServletRequest) request).getMethod();
                    if (INCLUDE_METHODS.contains(method.toUpperCase())) {
                        ServletRequest requestWrapper = new JsonRequestWrapper((HttpServletRequest) request);
                        chain.doFilter(requestWrapper, response);
                        return;
                    }
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // 未处理响应，可能导致后续过滤器继续操作流
            // 直接返回错误响应
            // 关键修复：类型转换 + 状态码设置
            if (response instanceof HttpServletResponse httpResponse) {
                try {
                    // 设置状态码为500（服务器内部错误）
                    httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    httpResponse.setContentType(CONTENT_TYPE_JSON);
                    httpResponse.setCharacterEncoding("UTF-8");

                    // 直接写入错误信息
                    String errorJson = "{\"code\":500, \"msg\":\"服务器内部错误\"}";
                    httpResponse.getWriter().write(errorJson);
                } catch (IOException ex) {
                    log.error("无法写入错误响应", ex);
                }
            }
            return; // 终止请求链，避免后续操作
        }
    }
}