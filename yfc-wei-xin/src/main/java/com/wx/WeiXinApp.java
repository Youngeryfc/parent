package com.wx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @创建人 yfc
 * @创建时间 2019-07-17 14:49
 * @描述
 */
@EnableEurekaClient
@SpringBootApplication
public class WeiXinApp {
	//获取微信签名
	public static void main(String [] args){
		SpringApplication.run(WeiXinApp.class,args);
	}
}
