package com.javagpt.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author JavaEdge
 * 处理json请求的过滤器
 */
public class JsonRequestFilter implements Filter {

    private static final String CONTENT_TYPE_JSON="application/json";
    private static final Set<String> INCLUDE_METHODS=new HashSet<>();

    static {
        INCLUDE_METHODS.add("POST");
        INCLUDE_METHODS.add("PUT");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            String contentType = request.getContentType();
            if (StringUtils.isNotBlank(contentType) && contentType.contains(CONTENT_TYPE_JSON)){
                String method = ((HttpServletRequest) request).getMethod();
                if (INCLUDE_METHODS.contains(method.toUpperCase())){
                    ServletRequest requestWrapper = new JsonRequestWrapper((HttpServletRequest) request);
                    chain.doFilter(requestWrapper, response);
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
    
}