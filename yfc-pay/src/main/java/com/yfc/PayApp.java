package com.yfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author yfc
 * @date 2019-07-19
 * @description
 * @parmas 支付服务
 */
@EnableEurekaClient
@SpringBootApplication
public class PayApp {
    public static void main(String[] args) {
        SpringApplication.run(PayApp.class,args);
    }
}
