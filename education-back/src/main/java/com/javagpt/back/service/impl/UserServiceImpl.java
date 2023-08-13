package com.javagpt.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javagpt.back.dto.ResultBody;
import com.javagpt.back.dto.UserDTO;
import com.javagpt.back.entity.UserEntity;
import com.javagpt.back.mapper.UserMapper;
import com.javagpt.back.service.UserService;
import com.javagpt.common.util.Md5Util;
import com.javagpt.common.util.VerifyCodeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * @author MSIK
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-08-05 18:49:02
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity>
        implements UserService {

    @Override
    public boolean chkUsername(String username) {
        UserEntity userEntity = this.getBaseMapper()
                .selectOne(new LambdaQueryWrapper<UserEntity>()
                        .eq(UserEntity::getUsername, username));
        return userEntity != null;
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

        String verifyCode = ( String ) request.getSession().getAttribute("verifyCode");
        log.info("获取验证码的值为: {}", verifyCode);
        if (!user.getValidCode().equalsIgnoreCase(verifyCode)) {
            user.setOk(false);
            user.setMessage("验证码输入错误！");
            return ResultBody.success(user);
        }
        user.setId(String.valueOf(new Date().getTime()));
        user.setPassword(Md5Util.getMD5(user.getPassword()));
        int num = insertUser(user);
        if (num <= 0) {
            return ResultBody.error("注册失败！");
        }
        user.setMessage("注册成功！");
        return ResultBody.success(user);

    }

    @Override
    public ResultBody doLogin(HttpServletRequest request, UserDTO user) {
        String verifyCode = ( String ) request.getSession().getAttribute("verifyCode");
        log.info("获取验证码的值为: {}", verifyCode);
        if (!user.getValidCode().equalsIgnoreCase(verifyCode)){
            user.setOk(false);
            user.setMessage("验证码输入错误！");
            return ResultBody.success(user);
        }
        UserEntity userEntity = selectUser(user.getUsername(), Md5Util.getMD5(user.getPassword()));
        if (userEntity == null) {
            return ResultBody.error("登录失败");
        }

        return  ResultBody.success(user);
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
            request.getSession().setAttribute("startTime",new Date());
            response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别
            OutputStream os = response.getOutputStream(); //获取文件输出流
            ImageIO.write(verifyImg, "png", os);//输出图片流
            os.flush();
            os.close();//关闭流
        } catch (IOException e) {
            log.error("获取验证码图片失败!", e);
        }
    }
}




