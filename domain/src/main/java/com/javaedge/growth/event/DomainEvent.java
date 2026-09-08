package com.javaedge.growth.event;

import java.time.Instant;

/**
 * 领域事件标记接口。
 *
 * <p>聚合根的行为不直接调用基础设施（不发 MQ、不写日志总线），
 * 只产生领域事件并由<b>应用层</b>负责发布到事件总线（Spring ApplicationEvent /
 * MQ / 本地 handler）。这样聚合根保持纯净、可单测，且领域事件天然可审计、可重放。
 */
public interface DomainEvent {

    /** 事件发生时间，用于审计与排序。 */
    Instant occurredOn();
}
