package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.LoginRespVO;
import com.javagpt.back.dto.UserDTO;
import com.javagpt.back.entity.UserEntity;
import com.javagpt.back.mapper.UserMapper;
import com.javagpt.back.service.UserService;
import com.javagpt.common.constant.ResultStatus;
import com.javagpt.common.resp.ResultBody;
import com.javagpt.common.util.Md5Util;
import com.javagpt.common.util.VerifyCodeUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
@Scope("singleton")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int checkUsername(String username) {
        UserEntity userEntity = this.getBaseMapper()
                .selectOne(new LambdaQueryWrapper<UserEntity>()
                        .eq(UserEntity::getUsername, username));
        return userEntity != null ? 1 : 0;
    }

    @Override
    public int insertUser(UserDTO user) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setCreatTime(new Date());
        userEntity.setUpdateTime(new Date());

        save(userEntity);
        return 1;
    }

    @Override
    public UserEntity selectUser(String username, String password) {

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getUsername, username)
                .eq(UserEntity::getPassword, password);

        UserEntity userEntity = this.getBaseMapper().selectOne(wrapper);
        return userEntity;
    }

    @Override
    public ResultBody register(HttpServletRequest request, UserDTO user) {

        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        log.info("获取验证码的值为: {}", verifyCode);
        if (!user.getValidCode().equalsIgnoreCase(verifyCode)) {
            user.setOk(false);
            user.setMessage("验证码输入错误！");
            return ResultBody.success(user);
        }
        if (checkUsername(user.getUsername()) == 1) {
            user.setOk(false);
            user.setMessage("用户名已存在！");
            return ResultBody.success(user);
        }
        user.setPassword(Md5Util.getMD5(user.getPassword()));
        int num = insertUser(user);
        if (num <= 0) {
            return ResultBody.error("注册失败！");
        }
        user.setMessage("注册成功！");
        return ResultBody.success(user);

    }

    @Override
    public ResultBody doLogin(HttpServletRequest request, HttpServletResponse response, UserDTO user) {
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        log.info("获取验证码的值为: {}", verifyCode);
        if (!user.getValidCode().equalsIgnoreCase(verifyCode)) {
            return ResultBody.error(ResultStatus.ERROR_CODE, "验证码输入错误!");
        }

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUsername, user.getUsername());
        UserEntity userEntity = userMapper.selectOne(wrapper);

        // 1. 用户不存在
        if (userEntity == null) {
            return ResultBody.error(ResultStatus.USER_NOT_FOUND, "登录失败,用户名不存在");
        }

        // 2. 用户密码错误
        String md5Pwd = Md5Util.getMD5(user.getPassword());
        // 数据库密码是MD5加密过的，MD5算法又不可逆，所以只能把登录密码加密后去和数据库的密码比对
        if (!userEntity.getPassword().equals(md5Pwd)) {
            return ResultBody.error(ResultStatus.USER_ERROR_PASSWORD, "用户密码错误");
        }

        // 3. 登录成功，生成token
        JwtBuilder builder = Jwts.builder();
        LoginRespVO loginRespVO = new LoginRespVO();

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", userEntity.getId());
        map.put("key2", "value2");
        //载荷部分，主题，就是token中携带的数据，这里把用户名放进去
        String token = builder.setSubject(userEntity.getUsername())
                //设置token的生成时间
                .setIssuedAt(new Date())
                //设置用户id为token  id ''是因为用户id是int类型，需要转换为字符串类型
                .setId(userEntity.getId().toString())
                //map中可以存放用户的角色权限信息
                .setClaims(map)
                //设置token过期时间，当前时间加一天就是时效为一天过期
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                //签名部分，设置HS256加密方式和加密密码,ycj123456是自定义的密码
                .signWith(SignatureAlgorithm.HS256, "JavaGPT")
                .compact();
        loginRespVO.setId(userEntity.getId());
        loginRespVO.setUsername(userEntity.getUsername());
        loginRespVO.setToken(token);
        Cookie cookie = new Cookie("token", URLEncoder.encode(token, StandardCharsets.UTF_8));
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResultBody.success(loginRespVO);
    }

    @Override
    public void getCheckCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            int width = 120;
            int height = 40;
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //生成对应宽高的初始图片
            String randomText = VerifyCodeUtil.drawRandomText(width, height, verifyImg);
            request.getSession().setAttribute("verifyCode", randomText);
            request.getSession().setAttribute("startTime", new Date());
            //必须设置响应内容类型为图片，否则前台不识别
            response.setContentType("image/png");
            //获取文件输出流
            OutputStream os = response.getOutputStream();
            //输出图片流
            ImageIO.write(verifyImg, "png", os);
            os.flush();
            os.close();//关闭流
        } catch (IOException e) {
            log.error("获取验证码图片失败!", e);
        }
    }

    @Override
    public ResultBody logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResultBody.success();
    }
}




