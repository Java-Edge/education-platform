package com.bubaiwantong.eurekacore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaCoreApplication.class, args);
    }

}
