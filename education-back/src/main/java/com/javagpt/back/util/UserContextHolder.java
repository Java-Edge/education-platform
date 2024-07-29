package com.javagpt.back.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.javagpt.common.constant.EPConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

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
        // 解析token的SigningKey必须和生成token时设置密码一致
        parser.setSigningKey("JavaGPT");
        //如果token检验通过（密码正确，有效期内）则正常执行，否则抛出异常
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        Integer userId = (Integer) claimsJws.getBody().get("userId");
        return userId;
    }
}