package com.javagpt.common.utils;

import org.springframework.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
/**
 * @author 10100586
 */
public class IpUtils {

    private static final String UNKNOWN_CONTENT = "unknown";

    private IpUtils() {
        throw new AssertionError("HttpRequestUtil can't be an instance");
    }

    public static String getIpAddress(HttpServletRequest request) {
        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ipAddresses) || UNKNOWN_CONTENT.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || UNKNOWN_CONTENT.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || UNKNOWN_CONTENT.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || UNKNOWN_CONTENT.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }
        if (StringUtils.hasLength(ipAddresses)) {
            //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
            return ipAddresses.split(",")[0];
        } else {
            //还是不能获取到，最后再通过request.getRemoteAddr();获取
            return request.getRemoteAddr();
        }
    }

}
