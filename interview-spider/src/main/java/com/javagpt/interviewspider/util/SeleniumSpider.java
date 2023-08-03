package com.javagpt.interviewspider.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
/**
 * 使用Java+Selenium 自动框架实现
 */
public class SeleniumSpider {
    public static void main(String [] args) throws InterruptedException{
        System.setProperty("webdriver.chrome.driver","D:\\Software\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.superbed.cn/");
//        driver.findElement(By.linkText("登录")).click();
        driver.findElement(By.className("login")).click();

        // 输入账号密码
        driver.findElement(By.name("username")).sendKeys("zqy12345");
        driver.findElement(By.name("password")).sendKeys("852230");

        // 点击登录
//        driver.findElement(By.linkText("立即提交")).click();
        driver.findElement(By.className("layui-btn")).click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 10);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("file")));

//        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
//
        driver.findElement(By.name("file")).click();



//        String text = username.getText();
//        System.out.println(text);
//        String firtHandle = driver.getWindowHandle();
//        driver.findElement(By.linkText("hao123新闻")).click();
//        Set<String> h = driver.getWindowHandles();
//        for(String handle : h){
//            System.out.print("当前遍历值为："+handle);
//            Thread.sleep(1000);
//            if(handle != firtHandle){
//                // 句柄切换
//                driver.switchTo().window(handle);
//            }
//        }
//        driver.findElement(By.linkText("一键登录")).click();
    }

}
