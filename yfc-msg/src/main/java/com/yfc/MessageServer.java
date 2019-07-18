package com.yfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author yfc
 * @date 2019-06-18
 * @description
 * @parmas
 */
@SpringBootApplication
@EnableEurekaClient
public class MessageServer {
    public static void main(String[] args) {
        SpringApplication.run(MessageServer.class,args);
    }
}
