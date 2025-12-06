package com.javaedge.common.util;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author JavaEdge
 * @description
 * @date 2022/2/22 13:35
 */
public class UrlUtils {

    public static final String SLASH = "/";

    /**
     * 拼接query
     *
     * @param url
     * @param query
     * @return
     */
    public static String appendQuery(String url, String query) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        if (StringUtils.isNotBlank(query)) {
            if (url.indexOf("?") > 0) {
                url = url + "&" + query;
            } else {
                url = url + "?" + query;
            }
        }
        return url;
    }

    /**
     * 从request解析,cas切换专用函数
     *
     * @param url
     * @param request
     * @return
     */
    public static String parseQuery(String url, HttpServletRequest request) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        String query = parseQuery(request);
        if (StringUtils.isNotBlank(query)) {
            if (url.indexOf("?") > 0) {
                url = url + "&" + query;
            } else {
                url = url + "?" + query;
            }
        }
        return url;
    }

    /**
     * 解析request中的参数,cas切换专用函数
     *
     * @param request
     * @return
     */
    public static String parseQuery(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            if ("ticket".equalsIgnoreCase(name)) {
                continue;
            }
            String value = request.getParameter(name);
            sb.append(name).append("=").append(URLEncoder.encode(value)).append("&");
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 拼接url上的path路径地址
     *
     * @param url
     * @param concatPath
     * @return
     */
    public static String appendUri(String url, String... concatPath) {
        StringBuffer sb = new StringBuffer();
        url = trimSlash(url);
        sb.append(url);
        if (concatPath != null && concatPath.length > 0) {
            String separator = SLASH;
            for (String item : concatPath) {
                if (StringUtils.isBlank(item)) {
                    continue;
                }
                if ((sb.toString().endsWith(separator) && !item.startsWith(separator)) || (!sb.toString().endsWith(separator) && item.startsWith(separator))) {
                    sb.append(item);
                } else if (!sb.toString().endsWith(separator) && !item.startsWith(separator)) {
                    sb.append(separator + item);
                } else {
                    int i = item.indexOf(separator);
                    sb.append(item.substring(i + 1));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 去除url后缀的斜杠"/"(可以去除多个)
     *
     * @param url
     * @return
     */
    public static String trimSlash(String url) {
        if (StringUtils.isNotBlank(url) && url.endsWith(SLASH)) {
            url = url.substring(0, url.length() - 1);
            url = trimSlash(url);
        }
        return url;
    }

    /**
     * 是否是http的url地址
     *
     * @param url
     * @return
     */
    public static boolean isHttpUrl(String url) {
        if (StringUtils.isNotBlank(url)) {
            if (url.startsWith("http") || url.startsWith("https")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解析字符串，是否是url地址，如果是非法字符那么返回null
     *
     * @param urlStr
     * @return
     */
    public static URL parseUrl(String urlStr) {
        if (isHttpUrl(urlStr)) {
            try {
                URL url = new URL(urlStr);
                return url;
            } catch (MalformedURLException e) {

            }
        }
        return null;
    }

    /**
     * 解析query字符串，将其转换为map对象，注意query字符串不包含开头的？
     *
     * @param queryStr
     * @return
     */
    public static Map<String, String> queryToMap(String queryStr) {
        Map<String, String> result = new HashMap<>();
        if (StringUtils.isNotBlank(queryStr)) {
            String[] items = queryStr.split("&");
            for (String item : items) {
                String[] keyValue = item.split("=");
                if (keyValue.length > 1) {
                    result.put(keyValue[0], URLDecoder.decode(keyValue[1]));
                } else {
                    result.put(keyValue[0], "");
                }
            }
        }
        return result;
    }

    /**
     * 解析字符串，且将解析之后的query转换为map对象
     *
     * @param urlStr
     * @return
     */
    public static Map<String, String> queryToMapFromUrl(String urlStr) {
        URL url = parseUrl(urlStr);
        if (url != null) {
            return queryToMap(url.getQuery());
        }
        return new HashMap<>();
    }

    /**
     * 将map参数转为query字符串
     *
     * @param paramMap
     * @return
     */
    public static String mapParamToQuery(Map<String, Object> paramMap) {
        if (MapUtils.isEmpty(paramMap)) {
            return "";
        }
        StringBuilder pathSb = new StringBuilder();
        String query = "";
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (StringUtils.isNotBlank(key)) {
                if (val == null) {
                    pathSb.append(key + "=&");
                } else {
                    pathSb.append(key + "=" + URLEncoder.encode(val + "") + "&");
                }
            }
        }
        if (pathSb.length() > 0) {
            query = pathSb.substring(0, pathSb.length() - 1);
        }
        return query;
    }
}
