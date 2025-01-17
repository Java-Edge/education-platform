package com.javagpt.common.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 当请求进入网关时，IPLimitFilter 会拦截请求。
 *
 * 如果请求的 URL 在限流列表中，则检查客户端 IP 是否超过限制。
 *
 * 如果超过限制，则返回 429 状态码；否则放行请求。
 * @author JavaEdge
 * @date 2025/1/14
 */
@Component
public class IPLimitFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final ExcludeUrlProperties excludeUrlProperties;

    public IPLimitFilter(ExcludeUrlProperties excludeUrlProperties) {
        this.excludeUrlProperties = excludeUrlProperties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取当前的请求路径
        String url = exchange.getRequest().getURI().getPath();

        // 2. 获得所有的需要进行 IP 限流校验的 URL 列表
        List<String> ipLimitList = excludeUrlProperties.getIpLimitUrls();

        // 3. 校验并且判断
        if (ipLimitList != null && !ipLimitList.isEmpty()) {
            for (String limitUrl : ipLimitList) {
                if (antPathMatcher.match(limitUrl, url)) {
                    // 如果匹配到，则表明需要进行 IP 的拦截校验
                    System.out.println("IPLimitFilter - 拦截到需要进行 IP 限流校验的方法：URL = " + url);
                    return doLimit(exchange, chain);
                }
            }
        }

        // 4. 默认直接放行
        return chain.filter(exchange);
    }

    private Mono<Void> doLimit(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 这里实现 IP 限流的逻辑
        // 例如：检查 IP 是否超过限制，如果超过则返回 429 Too Many Requests
        String clientIp = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        if (isIpOverLimit(clientIp)) {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().setComplete();
        }

        // 如果 IP 没有超过限制，则继续执行后续的过滤器链
        return chain.filter(exchange);
    }

    private boolean isIpOverLimit(String clientIp) {
        // 这里实现具体的 IP 限流逻辑
        // 例如：使用 Redis 或其他存储来记录 IP 的请求次数
        // 返回 true 表示 IP 超过限制，false 表示未超过限制
        return false; // 示例代码，默认返回 false
    }

    @Override
    public int getOrder() {
        // 设置过滤器的执行顺序
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

// 配置类，用于存储需要进行 IP 限流的 URL 列表
@Component
class ExcludeUrlProperties {
    private List<String> ipLimitUrls;

    public List<String> getIpLimitUrls() {
        return ipLimitUrls;
    }

    public void setIpLimitUrls(List<String> ipLimitUrls) {
        this.ipLimitUrls = ipLimitUrls;
    }
}
