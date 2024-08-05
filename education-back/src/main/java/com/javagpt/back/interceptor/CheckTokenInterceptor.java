package com.javagpt.back.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javagpt.common.constant.EPConstant;
import com.javagpt.common.constant.ResultStatus;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.common.constant.CommonConstants;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;

import static com.javagpt.common.constant.EPConstant.SIGNING_KEY;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (env.equals("dev")) {
            return true;
        }
        //关于浏览器的请求预检.在跨域的情况下，非简单请求会先发起一次空body的OPTIONS请求，称为"预检"请求，用于向服务器请求权限信息，等预检请求被成功响应后，才发起真正的http请求。
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }
        String token = request.getHeader(EPConstant.TOKEN);
        if (token == null) {
            ResultBody resultVO = new ResultBody(ResultStatus.LOGIN_FAIL_NOT, "请先登录！", null);
            doResponse(response, resultVO);
        } else {
            try {
                JwtParser parser = Jwts.parser();
                // 解析token的SigningKey必须和生成token时设置密码一致
                parser.setSigningKey(SIGNING_KEY);
                // 如token检验通过（密码正确，有效期内）则正常执行，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                Integer userId = (Integer) claimsJws.getBody().get("userId");
                if (userId != null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userId, null, null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                // 验证通过，放行
                return true;
            } catch (ExpiredJwtException e) {
                ResultBody resultVO = new ResultBody(ResultStatus.LOGIN_FAIL_OVERDUE, "登录过期，请重新登录！", null);
                doResponse(response, resultVO);
            } catch (UnsupportedJwtException e) {
                ResultBody resultVO = new ResultBody(ResultStatus.LOGIN_FAIL_NOT, "Token不合法，请自重！", null);
                doResponse(response, resultVO);
            } catch (Exception e) {
                ResultBody resultVO = new ResultBody(ResultStatus.LOGIN_FAIL_NOT, "请先登录！", null);
                doResponse(response, resultVO);
            }
        }
        return false;
    }

    /**
     * 没带token或者检验失败响应给前端
     *
     * @param response
     * @param resultVO
     * @throws IOException
     */
    private void doResponse(HttpServletResponse response, ResultBody resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding(CommonConstants.UTF_8);
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

    }

}
