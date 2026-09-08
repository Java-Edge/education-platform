package com.javaedge.application.growth;

import jakarta.validation.constraints.NotNull;

/**
 * 签到命令（应用层输入对象 / Command）。
 *
 * <p>命令对象封装"一次用户意图"，与领域内部的状态变更分离：应用层校验入参后，
 * 把它翻译成对聚合根的方法调用。这避免了 Controller 直接摆弄领域对象，保持分层清晰。
 */
public record SignCommand(@NotNull Integer userId) {
}
