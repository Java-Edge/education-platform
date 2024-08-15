//package com.javagpt.back.config.springsecurity;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.javagpt.common.resp.ResultBody;
//import com.javagpt.common.constant.ResultStatus;
//import com.javagpt.common.constant.CommonConstants;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@Component
//public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        ResultBody resultVO = new ResultBody(ResultStatus.AUTHORIZE_FAIL, "无权访问！", null);
//        doResponse(response, resultVO);
//    }
//
//    /**
//     * 没带token或者检验失败响应给前端
//     *
//     * @param response
//     * @param resultVO
//     * @throws IOException
//     */
//    private void doResponse(HttpServletResponse response, ResultBody resultVO) throws IOException {
//        response.setContentType("application/json");
//        response.setCharacterEncoding(CommonConstants.UTF_8);
//        PrintWriter out = response.getWriter();
//        String s = new ObjectMapper().writeValueAsString(resultVO);
//        out.print(s);
//        out.flush();
//        out.close();
//    }
//}
