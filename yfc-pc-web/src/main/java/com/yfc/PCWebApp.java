package com.yfc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author yfc
 * @date 2019-06-19
 * @description
 * @parmas
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.yfc.fegin")
public class PCWebApp {
    public static void main(String[] args) {
        SpringApplication.run(PCWebApp.class,args);
    }
}
