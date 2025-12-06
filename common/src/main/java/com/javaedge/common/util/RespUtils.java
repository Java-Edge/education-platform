package com.javaedge.common.util;

import com.alibaba.fastjson.JSON;
import com.javaedge.common.resp.GenericResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author JavaEdge
 * @date 2023/3/15
 */
public class RespUtils {

    public static void write(HttpServletResponse response, GenericResponse genericResponse) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSON(genericResponse));
    }

    public static void write(HttpServletResponse response, int status, GenericResponse genericResponse) throws IOException {
        response.setStatus(status); //HttpServletResponse.SC_OK
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSON(genericResponse));
    }
}
