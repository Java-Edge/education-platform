package com.javagpt.common.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @description: 生成验证码工具类
 * @projectName:personal
 * @author:yuzonghao
 * @createTime:2020/3/7 12:57
 */
public class VerifyCodeUtil {

    private static String baseNumLetter = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
    private static String font = "微软雅黑";

    /**
     * description: 绘制验证码图片,返回验证码文本内容
     * param [width, height, verifyImg]
     * author yuzonghao
     * createTime 2020/3/7 13:06
     **/
    public static  String drawRandomText(int width, int height, BufferedImage verifyImg) {

        Graphics2D graphics = (Graphics2D) verifyImg.getGraphics();
        //设置画笔颜色-验证码背景色
        graphics.setColor(Color.WHITE);
        //填充背景
        graphics.fillRect(0, 0, width, height);
        graphics.setFont(new Font(font, Font.BOLD, 30));

        StringBuffer sBuffer = new StringBuffer();
        //旋转原点的 x 坐标
        int x = 10;
        String ch = "";
        Random random = new Random();
        for(int i = 0;i < 4;i++){
            graphics.setColor(getRandomColor());
            //设置字体旋转角度,角度小于10度
            int degree = random.nextInt() % 10;
            int dot = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            sBuffer.append(ch);
            //正向旋转
            graphics.rotate(degree * Math.PI / 180, x, 30);
            graphics.drawString(ch, x, 30);
            //反向旋转
            graphics.rotate(-degree * Math.PI / 180, x, 30);
            x += 25;
        }

        //画干扰线
        for (int i = 0; i <15; i++) {
            // 设置随机颜色
            graphics.setColor(getRandomColor());
            // 随机画线
            graphics.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));

        }

        //添加噪点
        for(int i=0;i<15;i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.setColor(getRandomColor());
            graphics.fillRect(x1, y1, 2,2);

        }
        return sBuffer.toString();
    }


    /**
     * description: 获取随机颜色
     * param []
     * author yuzonghao
     * createTime 2020/3/7 12:55
     **/
    private static Color getRandomColor(){
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256),
                ran.nextInt(256),ran.nextInt(256));
        return color;
    }
}


