package com.javagpt.common.mail;

/**
 * @author liuweijie
 * @date 2021/11/16 13:39
 */
public enum MailTemplateEnum {
    /**
     * 测试
     */
    test("test.html", "测试"),
    /**
     * 注册验证码
     */
    accountRegisterPin("account.register.pin.html", "注册验证码"),
    /**
     * 重置密码验证码
     */
    accountResetPasswordPin("account.reset.password.pin.html", "重置密码验证码"),

    /**
     * 账号注册已通过审核
     */
    enterpriseRegisterSuccess("enterprise.register.success.html", "主体审核通过"),
    /**
     * 企业信息注册已完成
     */
    enterpriseRegisterFail("enterprise.register.fail.html", "主体审核退回"),
    ;


    private final String fileName;
    private final String subject;

    MailTemplateEnum(String fileName, String subject) {
        this.fileName = fileName;
        this.subject = subject;
    }

    public String fileName() {
        return fileName;
    }

    public String subject() {
        return subject;
    }
}
