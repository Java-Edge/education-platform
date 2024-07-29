package com.javagpt.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.LoginRespVO;
import com.javagpt.application.user.UserDTO;
import com.javagpt.back.entity.UserPO;
import com.javagpt.back.mapper.UserMapper;
import com.javagpt.back.service.UserService;
import com.javagpt.common.constant.EPConstant;
import com.javagpt.common.constant.ResultStatus;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.common.util.Md5Util;
import com.javagpt.common.util.VerifyCodeUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

import static com.javagpt.common.constant.EPConstant.TOKEN_EXPIRE_TIME;

@Service
@Slf4j
@Scope("singleton")
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {

    private final UserMapper userMapper;

    private final RedissonClient redissonClient;

    @Override
    public int checkUsername(String username) {
        UserPO userPO = this.getBaseMapper()
                .selectOne(new LambdaQueryWrapper<UserPO>()
                        .eq(UserPO::getUsername, username));
        return userPO != null ? 1 : 0;
    }

    @Override
    public int insertUser(UserDTO user) {
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(user, userPO);
        userPO.setUpdateTime(new Date());
        userPO.setCreateTime(new Date());
        save(userPO);
        return 1;
    }

    @Override
    public UserPO selectUser(String username, String password) {
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<UserPO>()
                .eq(UserPO::getUsername, username)
                .eq(UserPO::getPassword, password);

        return this.getBaseMapper().selectOne(wrapper);
    }

    @Override
    public ResultBody register(HttpServletRequest request, UserDTO user) {
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        log.info("获取验证码的值为: {}", verifyCode);
        if (!user.getValidCode().equalsIgnoreCase(verifyCode)) {
            return ResultBody.error("验证码输入错误！");
        }
        if (checkUsername(user.getUsername()) == 1) {
            log.error("用户名已存在 user={}", JSON.toJSONString(user));
            return ResultBody.error("用户名已存在！请更换一个昵称");
        }
        user.setPassword(Md5Util.getMD5(user.getPassword()));
        int num = insertUser(user);
        if (num <= 0) {
            return ResultBody.error("注册失败！");
        }
        return ResultBody.success(user);

    }

    @Override
    public ResultBody doLogin(HttpServletRequest request, HttpServletResponse response, UserDTO user) {
        // 使用 Redis 锁一个账号只能购买一次
        String lockName = "loginUser";
        // Redisson 分布式锁
        RLock lock = redissonClient.getLock(lockName);

        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        if (!user.getValidCode().equalsIgnoreCase(verifyCode)) {
            log.error("登录接口获取到客户端请求的验证码为 {},和实际验证码 {} 不符", verifyCode, user.getValidCode());
            return ResultBody.error(ResultStatus.ERROR_CODE, "验证码输入错误!");
        }

        UserPO userPO = userMapper.selectOne(new LambdaQueryWrapper<UserPO>().eq(UserPO::getUsername, user.getUsername()));

        // 1. 用户不存在
        if (userPO == null) {
            return ResultBody.error(ResultStatus.USER_NOT_FOUND, "登录失败,用户名不存在");
        }

        // 2. 用户密码错误
        String md5Pwd = Md5Util.getMD5(user.getPassword());
        // 数据库密码是MD5加密过的，MD5算法又不可逆，所以只能把登录密码加密后去和数据库的密码比对
        if (!userPO.getPassword().equals(md5Pwd)) {
            return ResultBody.error(ResultStatus.USER_ERROR_PASSWORD, "用户密码错误");
        }

        // 3. 登录成功，生成token
        LoginRespVO loginRespVO = new LoginRespVO();

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userPO.getId());
        map.put("key2", "value2");
        String token = Jwts.builder()
                // token中携带的数据，这里把用户名放进去
                .setSubject(userPO.getUsername())
                //设置token的生成时间
                .setIssuedAt(new Date())
                //设置用户id为token
                .setId(userPO.getId().toString())
                // 用户的角色权限信息
                .setClaims(map)
                // 会话
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME * 1000))
                // 签名部分，设置HS256加密方式和加密密码,ycj123456 是自定义的密码
                .signWith(SignatureAlgorithm.HS256, "JavaGPT")
                .compact();
        loginRespVO.setId(userPO.getId());
        loginRespVO.setUsername(userPO.getUsername());
        loginRespVO.setToken(token);
        Cookie cookie = new Cookie(EPConstant.TOKEN, URLEncoder.encode(token, StandardCharsets.UTF_8));
        cookie.setMaxAge(TOKEN_EXPIRE_TIME);
        cookie.setPath("/");
        response.addCookie(cookie);
        userMapper.updateById(userPO);

        return ResultBody.success(loginRespVO);
    }

    @Override
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response) {
        int width = 120;
        int height = 40;
        try {
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 生成对应宽高的初始图片
            String randomText = VerifyCodeUtil.drawRandomText(width, height, verifyImg);
            request.getSession().setAttribute("verifyCode", randomText);
            request.getSession().setAttribute("startTime", new Date());
            // 须设置响应内容类型为图片，否则前台不识别
            response.setContentType("image/png");
            try (OutputStream os = response.getOutputStream()) {
                ImageIO.write(verifyImg, "png", os);
            }
        } catch (IOException e) {
            log.error("获取验证码图片失败!", e);
        }
    }

    @Override
    public ResultBody logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(EPConstant.TOKEN, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResultBody.success();
    }
}