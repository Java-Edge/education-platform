package com.javagpt.common.utils;

import com.alibaba.fastjson.JSON;
import com.javagpt.common.resp.RespResult;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author JavaEdge
 * @date 2023/3/15
 */
public class RespUtils {

    public static void write(HttpServletResponse response, RespResult respResult) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSON(respResult));
    }

    public static void write(HttpServletResponse response, int status, RespResult respResult) throws IOException {
        response.setStatus(status); //HttpServletResponse.SC_OK
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSON(respResult));
    }
}
