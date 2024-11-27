package com.javagpt.back.config;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author JavaEdge
 * @date 2023/9/11
 */
@Component
@Getter
@Slf4j
public class NacosConfig {

    @NacosValue(value = "${ftp.server.ip}", autoRefreshed = true)
    private String ftpServerPort;


    @NacosConfigListener(groupId = "education-platform", dataId = "application.yml")
    public void onChange(String value) {
        log.info("onChange String = {}", value);
    }
}
