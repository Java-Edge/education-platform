package com.javagpt.back.config.springsecurity;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        System.out.println(token);
        if (!StringUtils.isBlank(token)) {
            JwtParser parser = Jwts.parser();
            // 解析token的SigningKey必须和生成token时设置密码一致
            parser.setSigningKey("JavaGPT");
            //如果token检验通过（密码正确，有效期内）则正常执行，否则抛出异常
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            Integer userId = (Integer) claimsJws.getBody().get("userId");
            if (userId == null) {
                filterChain.doFilter(request, response);
            }
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userId,null,null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //放行
        filterChain.doFilter(request, response);
    }
}
