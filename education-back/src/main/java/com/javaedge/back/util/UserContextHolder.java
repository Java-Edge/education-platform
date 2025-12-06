package com.javaedge.back.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.javaedge.common.constant.EPConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import static com.javaedge.common.constant.EPConstant.SIGNING_KEY;

/**
 * @author 千祎来了
 * @date 2023/10/18 21:27
 */
public class UserContextHolder {

    public static Integer getCurrentUserId(HttpServletRequest request) {
        String token = request.getHeader(EPConstant.TOKEN);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        JwtParser parser = Jwts.parser();
        // 解析token的SigningKey须和生成token时设置密码一致
        parser.setSigningKey(SIGNING_KEY);
        // 如果token检验通过（密码正确，有效期内）则正常执行，否则抛异常
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        return (Integer) claimsJws.getBody().get("userId");
    }
}