package com.javagpt.common.utils;


import jakarta.servlet.http.HttpServletRequest;

/**
 * @author JavaEdge
 * @description
 * @date 2022/2/11 15:46
 */
public class RequestBodyUtils {

    public static final String JSON_BODY = "JSON_BODY";

    /**
     * 设置jsonBody
     *
     * @param request
     * @param jsonBody
     */
    public static void setJsonBodyAttr(HttpServletRequest request, String jsonBody) {
        request.setAttribute(JSON_BODY, jsonBody);
    }

    /**
     * 获取请求的jsonBody
     *
     * @param request
     * @return
     */
    public static String getJsonBodyAttr(HttpServletRequest request) {
        return (String) request.getAttribute(JSON_BODY);
    }

}
