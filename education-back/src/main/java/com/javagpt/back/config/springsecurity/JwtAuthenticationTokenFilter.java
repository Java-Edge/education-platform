package com.javagpt.back.config.springsecurity;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javagpt.common.constant.EPConstant;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.common.constant.ResultStatus;
import groovy.util.logging.Slf4j;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader(EPConstant.TOKEN);
        if (!StringUtils.isBlank(token)) {
            try {
                JwtParser parser = Jwts.parser();
                // 解析token的SigningKey必须和生成token时设置密码一致
                parser.setSigningKey("JavaGPT");
                /**
                 * 如果token检验通过（密码正确，有效期内）则正常执行，否则抛出异常
                 * 前端浏览器携带的 token 可能是上次登陆时存储下来的，因此需要捕获到异常，并抛出
                 */
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                Integer userId = (Integer) claimsJws.getBody().get("userId");
                if (userId == null) {
                    filterChain.doFilter(request, response);
                }
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userId,null,null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
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
        // 放行
        filterChain.doFilter(request, response);
    }

    /**
     * 没带token或者检验失败响应给前端
     */
    private void doResponse(HttpServletResponse response, ResultBody resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }
}